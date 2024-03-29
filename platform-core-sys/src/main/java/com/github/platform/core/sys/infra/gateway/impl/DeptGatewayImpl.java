package com.github.platform.core.sys.infra.gateway.impl;

import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.persistence.mapper.sys.SysDeptMapper;
import com.github.platform.core.persistence.mapper.sys.SysUserMapper;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.sys.domain.common.entity.SysDeptBase;
import com.github.platform.core.sys.domain.common.entity.SysUserBase;
import com.github.platform.core.sys.domain.constant.DeptConstant;
import com.github.platform.core.sys.domain.context.SysDeptContext;
import com.github.platform.core.sys.domain.context.SysDeptQueryContext;
import com.github.platform.core.sys.domain.dto.SysDeptDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;
import com.github.platform.core.sys.domain.gateway.ISysDeptGateway;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.convert.SysDeptInfraConvert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 部门网关
 *
 * @author: yxkong
 * @date: 2023/1/4 4:04 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class DeptGatewayImpl extends BaseGatewayImpl implements ISysDeptGateway {
    @Resource
    private SysDeptMapper sysDeptMapper;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysDeptInfraConvert convert;
    @Override
    public List<SysDeptDto> queryList(SysDeptQueryContext context) {
        SysDeptBase sysDept = convert.toSysDeptBase(context);
        //系统管理员默认查询全部,非管理默认只能查自己租户下的
        if (!AuthUtil.isSuperAdmin()) {
            sysDept.setTenantId(LoginUserInfoUtil.getTenantId());
        }
        sysDept.setIdGtZero(true);
        List<SysDeptBase> list = sysDeptMapper.findListBy(sysDept);
        return convert.toDtos(list);
    }
    @Override
    public void insert(SysDeptContext context) {
        SysDeptBase deptDO = convert.toSysDeptBase(context);
        deptDO.setId(null);
        if (deptDO.getParentId() == 0 && !AuthUtil.isSuperAdmin()){
            exception(SysInfraResultEnum.DEPT_ADD_FAIL);
        }
        //查询是否存在部门
        int existDept = sysDeptMapper.isExistDept(null, deptDO.getParentId(), deptDO.getDeptName());
        if (existDept > 0) {

            exception(SysInfraResultEnum.DEPT_EXIST);
        }
        SysDeptBase parent = sysDeptMapper.findById(deptDO.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (DeptConstant.DEPT_DISABLE.equals(parent.getStatus())) {
            exception(SysInfraResultEnum.DEPT_DISABLE);
        }
        deptDO.setAncestors(parent.getAncestors() + "," + deptDO.getParentId());
        deptDO.setTenantId(parent.getTenantId());
        sysDeptMapper.insert(deptDO);
    }

    @Override
    public void update(SysDeptContext context) {
        SysDeptBase dept = convert.toSysDeptBase(context);
        SysDeptBase sysDept = sysDeptMapper.findById(dept.getId());
        //更新部门不能修改对应的层级
        dept.setParentId(null);
        //查询是否存在部门
        int  existDept = sysDeptMapper.isExistDept(dept.getId(), sysDept.getParentId(), sysDept.getDeptName());
        if (existDept > 0 ) {
            exception(SysInfraResultEnum.DEPT_EXIST);
        }
        sysDeptMapper.updateById(dept);
    }

    @Override
    public void delete(Long id) {
        int subDeptCount = sysDeptMapper.findListByCount(SysDeptBase.builder().parentId(id).build());
        if (subDeptCount > 0) {
            exception(SysInfraResultEnum.DEPT_DELETE_EXIST_SUB_DEPT);
        }
        long userCount = sysUserMapper.findListByCount(SysUserBase.builder().deptId(id).build());
        if (userCount > 0) {
            exception(SysInfraResultEnum.DEPT_DELETE_EXIST_USER);
        }
        sysDeptMapper.deleteById(id) ;
    }

    @Override
    public List<TreeSelectDto> deptTree() {
        SysDeptBase sysDept = SysDeptBase.builder().status(StatusEnum.ON.getStatus()).build();
        /**暂时不实现租户*/
//        if (!AuthUtil.isAdmin()) {
//            //非管理员只取租户
//            sysDept.setTenant(LoginUserInfoUtil.getTenant());
//        }
        List<SysDeptBase> list = sysDeptMapper.findListBy(sysDept);
        return  buildDeptTreeSelect(list);
    }

//    @Override
//    public Long insertTenantDept(String deptName, String leader, String phone, Integer tenantId) {
//        SysDeptBase deptDO =  SysDeptBase.builder()
//                .deptName(deptName)
//                .leader(leader)
//                .leaderMobile(phone)
//                .tenant(tenant)
//                .parentId(100L)
//                .build();
//        //查询已有部门
//        if (UserConstants.NOT_UNIQUE.equals(sysDeptService.checkDeptNameUnique(deptDO))) {
//            return ResultBeanUtil.fail("新增部门'" + deptDO.getDeptName() + "'失败，部门名称已存在",null);
//        }
//        SysDeptBase info = sysDeptService.findById(deptDO.getParentId());
//        deptDO.setAncestors(info.getAncestors() + "," + deptDO.getParentId());
//        int row = sysDeptService.insert(deptDO);
//        return row > 0 ? ResultBeanUtil.success(deptDO.getId()) : ResultBeanUtil.fail("添加失败",null);
//        return null;
//    }

    @Override
    public List<SysDeptDto> findAllDept(Long deptId) {
        if (Objects.isNull(deptId)) {
            return null;
        }
        SysDeptBase sysDept = sysDeptMapper.findById(deptId);
        if (Objects.isNull(sysDept)) {
            return null;
        }

        String ancestors = sysDept.getAncestors() + "," + sysDept.getId();
        List<SysDeptBase> list = new ArrayList<>();
        list.add(sysDept);
        List<SysDeptBase> deptList = sysDeptMapper.findListBy(SysDeptBase.builder().ancestors(ancestors).build());
        if (!CollectionUtil.isEmpty(deptList)) {
            list.addAll(deptList);
        }
        return convert.toDtos(list);
    }

    @Override
    public SysDeptDto findById(Long id) {
        SysDeptBase sysDept = sysDeptMapper.findById(id);
        return convert.toDto(sysDept);
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param list
     * @return
     */
    private List<TreeSelectDto> buildDeptTreeSelect(List<SysDeptBase> list) {
        //获取当前
        List<SysDeptDto> deptTrees = buildDeptTree(convert.toDtos(list));
        return deptTrees.stream().map(TreeSelectDto::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     * @param depts 部门列表
     * @return 树结构列表
     */
    private List<SysDeptDto> buildDeptTree(List<SysDeptDto> depts) {
        List<SysDeptDto> rst = new ArrayList<>();
        List<SysDeptDto> roots = getChild(depts,DeptConstant.ROOT_ID);
        for (SysDeptDto parent : roots) {
            collectionChild(depts, parent);
            rst.add(parent);
        }
        if (rst.isEmpty()) {
            rst = depts;
        }
        return rst;
    }

    /**
     * 收集子节点
     */
    private void collectionChild(List<SysDeptDto> list, SysDeptDto parent) {
        // 得到子节点列表
        List<SysDeptDto> childList = getChild(list,parent.getId());
        parent.setChildren(childList);
        //递归收集
        for (SysDeptDto child : childList) {
            if (hasChild(list, child.getId())) {
                collectionChild(list, child);
            }
        }
    }

    /**
     * 得到子节点列表
     * @param list 所有的节点
     * @param parentId 父节点
     * @return
     */
    private List<SysDeptDto> getChild(List<SysDeptDto> list, Long parentId) {
        return list.stream().filter(s-> s.getParentId().equals(parentId)).collect(Collectors.toList());
    }

    /**
     * 判断是否有子节点
     * @param list
     * @param parentId
     * @return
     */
    private boolean hasChild(List<SysDeptDto> list, Long parentId) {
        SysDeptDto sysDept = list.stream().filter(s -> s.getParentId().equals(parentId)).findAny().orElse(null);
        return Objects.nonNull(sysDept);
    }
}
