package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.application.constant.SysAppResultEnum;
import com.github.platform.core.sys.application.executor.ISysCascadeExecutor;
import com.github.platform.core.sys.domain.constant.DeptConstant;
import com.github.platform.core.sys.domain.context.SysCascadeContext;
import com.github.platform.core.sys.domain.context.SysCascadeQueryContext;
import com.github.platform.core.sys.domain.dto.SysCascadeDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;
import com.github.platform.core.sys.domain.gateway.ISysCascadeGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 级联表执行器
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-12-01 20:57:44.667
 * @version 1.0
 */
@Service
@Slf4j
public class SysCascadeExecutorImpl extends SysExecutor implements ISysCascadeExecutor{
    @Resource
    private ISysCascadeGateway sysCascadeGateway;
    /**
    * 查询级联表列表
    * @param context 查询上下文
    * @return 分页结果
    */
    @Override
    public PageBean<SysCascadeDto> query(SysCascadeQueryContext context){
        context.setTenantId(getTenantId(context));
        PageBean<SysCascadeDto> pageBean = sysCascadeGateway.query(context);
        if (CollectionUtil.isNotEmpty(pageBean.getData())){
            pageBean.getData().forEach(s->{
                s.setHasChildren(s.isParent());
            });
        }
        return pageBean;
    }

    @Override
    public List<SysCascadeDto> list(SysCascadeQueryContext context) {
        context.setTenantId(getTenantId(context));
        List<SysCascadeDto> list = sysCascadeGateway.list(context);
        if (CollectionUtil.isNotEmpty(list)){
            list.forEach(s->{
                s.setHasChildren(s.isParent());
            });
        }
        return list;
    }

    @Override
    public List<TreeSelectDto> tree(SysCascadeQueryContext context) {
        context.setTenantId(getTenantId(context));
        //获取主节点信息
        SysCascadeDto parent = null;
        if (Objects.nonNull(context.getParentId())){
            parent = SysCascadeDto.builder().id(context.getParentId()).build();
        } else if (Objects.nonNull(context.getCode())){
            parent = sysCascadeGateway.findByCode(context.getCode(),context.getTenantId());
        }
        if (Objects.isNull(parent)){
            return Collections.emptyList();
        }
        List<SysCascadeDto> list = getAllChildren(parent.getId());
        return buildTreeSelect(list, parent.getId());
    }

    private  List<TreeSelectDto> buildTreeSelect(List<SysCascadeDto> list, Long rootParentId) {
        // 创建顶级节点
        List<TreeSelectDto> tree = new ArrayList<>();
        List<SysCascadeDto> rootList = list.stream().filter(s -> s.getParentId().equals(rootParentId)).collect(Collectors.toList());


        // 递归生成树
        for (SysCascadeDto item : rootList) {
            tree.add(buildTree(item, list));
        }
        return tree;
    }

    private  TreeSelectDto buildTree(SysCascadeDto node, List<SysCascadeDto> list) {
        TreeSelectDto treeNode = new TreeSelectDto();
        treeNode.setId(node.getId());
        treeNode.setLabel(node.getName());
        treeNode.setValue(node.getCode());

        // 递归子节点
        List<SysCascadeDto> children = list.stream().filter(s->s.getParentId().equals(node.getId())).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(children)) {
            treeNode.setChildren(children.stream()
                    .map(child -> buildTree(child, list))
                    .collect(Collectors.toList()));
        }
        return treeNode;
    }
    private List<SysCascadeDto> getAllChildren(Long parentId){
        List<SysCascadeDto> result = new ArrayList<>();
        collectChildren(parentId, result);
        return result;
    }

    private void collectChildren(Long parentId, List<SysCascadeDto> result) {
        List<SysCascadeDto> children = sysCascadeGateway.findByParentId(parentId);
        if (CollectionUtil.isNotEmpty(children)) {
            result.addAll(children);
            for (SysCascadeDto child : children) {
                if (child.isParent()){
                    collectChildren(child.getId(), result);
                }
            }
        }
    }

    /**
    * 新增级联表
    * @param context 新增上下文
    */
    @Override
    public SysCascadeDto insert(SysCascadeContext context){
        context.setTenantId(getTenantId(context));
        //默认为顶级节点
        String ancestors = "0";
        if (context.getParentId() > DeptConstant.ROOT_ID){
            SysCascadeDto dto = sysCascadeGateway.findById(context.getParentId());
            if (Objects.isNull(dto)){
                throw exception(SysAppResultEnum.CASCADE_DONT_EXIST);
            }
            //更新为非叶子节点
            sysCascadeGateway.update(SysCascadeContext.builder().id(dto.getId()).code(dto.getCode()).leaf(StatusEnum.OFF.getStatus()).build());
            ancestors = dto.getAncestors() + SymbolConstant.comma + dto.getId();
        }
        context.setAncestors(ancestors);
        //默认新增为叶子节点
        context.setLeaf(StatusEnum.ON.getStatus());
        SysCascadeDto dto = sysCascadeGateway.insert(context);
        dto.setHasChildren(dto.isParent());
        return dto;
    }
    /**
    * 根据id查询级联表明细
    * @param id 主键
    * @return 单条记录
    */
    @Override
    public SysCascadeDto findById(Long id) {
        return sysCascadeGateway.findById(id);
    }
    /**
    * 修改级联表
    * @param context 更新上下文
    */
    @Override
    public void update(SysCascadeContext context) {
        context.setTenantId(getTenantId(context));
        //不改变叶子节点的属性
        context.setLeaf(null);
        Pair<Boolean, SysCascadeDto> update = sysCascadeGateway.update(context);
        if (!update.getKey()){
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
    }
    /**
    * 根据id删除级联表记录
    * @param id 主键
    */
    @Override
    public void delete(Long id) {
        /**此处是为了再gateway上做多条件缓存，如果有必要，先查，后设置值*/
        SysCascadeContext context = SysCascadeContext.builder().id(id).build();
        SysCascadeDto dto = sysCascadeGateway.findById(id);
        int d = sysCascadeGateway.delete(context);
        List<SysCascadeDto> list = sysCascadeGateway.findByParentId(dto.getParentId());
        if (CollectionUtil.isEmpty(list)){
            sysCascadeGateway.update(SysCascadeContext.builder().id(dto.getParentId()).leaf(StatusEnum.ON.getStatus()).build());
        }
        if (d <=0 ){
            throw exception(ResultStatusEnum.COMMON_DELETE_ERROR);
        }
    }
}
