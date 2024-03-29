package com.github.platform.core.file.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.file.domain.common.entity.SysUploadFileBase;
import com.github.platform.core.file.domain.context.SysUploadFileContext;
import com.github.platform.core.file.domain.context.SysUploadFileQueryContext;
import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.file.domain.gateway.ISysUploadFileGateway;
import com.github.platform.core.file.infra.convert.SysUploadFileInfraConvert;
import com.github.platform.core.persistence.mapper.file.SysUploadFileMapper;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 上传文件表网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
@Service
public class SysUploadFileGatewayImpl implements ISysUploadFileGateway {

    @Resource
    private SysUploadFileMapper sysUploadFileMapper;
    @Resource
    private SysUploadFileInfraConvert convert;
    @Override
    public PageBean<SysUploadFileDto> query(SysUploadFileQueryContext context) {
        SysUploadFileBase sysUploadFileBase = convert.toSysUploadFileBase(context);

        PageHelper.startPage( context.getPageNum(), context.getPageSize());
        List<SysUploadFileBase> list = sysUploadFileMapper.findListBy(sysUploadFileBase);
        return convert.ofPageBean(new PageInfo<>(list));
    }

    @Override
    public List<SysUploadFileDto> findListBy(SysUploadFileQueryContext context) {
        SysUploadFileBase sysUploadFileBase = convert.toSysUploadFileBase(context);
        List<SysUploadFileBase> list = sysUploadFileMapper.findListBy(sysUploadFileBase);
        return convert.toDtos(list);
    }

    @Override
    public SysUploadFileDto insert(SysUploadFileContext context) {
        SysUploadFileBase sysUploadFileBase = convert.toSysUploadFileBase(context);
        int flag = sysUploadFileMapper.insert(sysUploadFileBase);
        return convert.toDto(sysUploadFileBase);
    }

    @Override
    public SysUploadFileDto findById(Long id) {
        SysUploadFileBase sysUploadFileBase = sysUploadFileMapper.findById(id);
        return convert.toDto(sysUploadFileBase);
    }

    @Override
    public Pair<Boolean, String> update(SysUploadFileContext context) {
        SysUploadFileBase sysUploadFileBase = convert.toSysUploadFileBase(context);
        int flag = sysUploadFileMapper.updateById(sysUploadFileBase);
        return flag>0 ? Pair.of(true, "修改成功！") : Pair.of(false, "修改失败！");
    }

    @Override
    public Pair<Boolean, String> delete(Long id) {
        int flag = sysUploadFileMapper.deleteById(id);
        return flag >0 ? Pair.of(true, "删除成功！") : Pair.of(false, "删除失败！");
    }
}
