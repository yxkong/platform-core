package com.github.platform.core.file.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.file.application.executor.ISysUploadFileExecutor;
import com.github.platform.core.file.application.executor.IUploadFileExecutor;
import com.github.platform.core.file.domain.context.SysUploadFileContext;
import com.github.platform.core.file.domain.context.SysUploadFileQueryContext;
import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.file.domain.gateway.ISysUploadFileGateway;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
* 上传文件表执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
@Service
@Slf4j
public class SysUploadFileExecutorImpl extends BaseExecutor implements ISysUploadFileExecutor {
    @Resource
    private ISysUploadFileGateway gateway;
    @Resource
    private IUploadFileExecutor uploadFileExecutor;
    @Override
    public PageBean<SysUploadFileDto> query(SysUploadFileQueryContext context){
        PageBean<SysUploadFileDto> page = gateway.query(context);
        if (Objects.nonNull(page) && CollectionUtil.isNotEmpty(page.getData())){
            page.getData().forEach(s->{
                s.setUrl(uploadFileExecutor.getUrl(s));
                s.setThumbUrl(uploadFileExecutor.getThumbUrl(s));
            });
        }
        return page;
    };
    @Override
    public void insert(SysUploadFileContext context){
         gateway.insert(context);
    }
    @Override
    public SysUploadFileDto findById(Long id) {
        return gateway.findById(id);
    }
    @Override
    public void update(SysUploadFileContext context) {
        gateway.update(context);
    }
    @Override
    public void delete(Long id) {
        gateway.delete(id);
    }
}
