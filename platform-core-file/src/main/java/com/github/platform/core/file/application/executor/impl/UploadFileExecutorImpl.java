package com.github.platform.core.file.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.file.application.executor.IUploadFileExecutor;
import com.github.platform.core.file.domain.context.SysUploadFileQueryContext;
import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.file.domain.entity.UploadEntity;
import com.github.platform.core.file.domain.gateway.ISysUploadFileGateway;
import com.github.platform.core.file.infra.configuration.properties.UploadProperties;
import com.github.platform.core.file.infra.convert.SysUploadFileInfraConvert;
import com.github.platform.core.file.infra.service.IUploadFileService;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 文件上传执行器
 * @author: yxkong
 * @date: 2022/8/3 3:54 PM
 * @version: 1.0
 */
@Service("uploadFileExecutor1")
@Slf4j
public class UploadFileExecutorImpl extends BaseExecutor implements IUploadFileExecutor {
    @Resource
    private Map<String, IUploadFileService> maps;
    @Resource
    private UploadProperties properties;
    @Resource
    private ISysUploadFileGateway sysUploadFileGateway;
    @Resource
    private SysUploadFileInfraConvert convert;
    @Override
    public UploadEntity uploadAndSave(String module, String bizNo, String path, String fileName,Long fileSize,  byte[] fileBytes) {
        if (log.isInfoEnabled()){
            log.info("module:{} bizNo:{} path:{} fileName:{} fileSize:{}",module,bizNo,path,fileName,fileSize);
        }
        //上传的时候用系统配置的
        IUploadFileService uploadFileService = maps.get(properties.getStorage() + "UploadFileService");
        if (Objects.isNull(uploadFileService)){
            log.warn("未找到对应存储类型{}的实现",properties.getStorage());
            throw exception(ResultStatusEnum.NO_FOUND_IMPLEMENT);
        }
        SysUploadFileDto uploadFileDto = uploadFileService.uploadAndSave(module, bizNo, fileName, fileSize, fileBytes);
        uploadFileDto.setPermanent(true);
        return UploadEntity.builder()
                .storage(properties.getStorage().name())
                .fileId(uploadFileDto.getFileId())
                .url(uploadFileService.getUrl(uploadFileDto))
                .thumbUrl(uploadFileService.getThumbUrl(uploadFileDto))
                .build() ;
    }

    @Override
    public void deleteByBizNo(String module, String bizNo) {
        List<SysUploadFileDto> list = sysUploadFileGateway.findListBy(SysUploadFileQueryContext.builder().module(module).bizNo(bizNo).build());
    }

    @Override
    public String uploadExcel(String fileName,String localPath) throws FileNotFoundException {
        byte[] fileBytes = null;
        // 使用 try-with-resources 确保流被关闭
        try (FileInputStream inputStream = new FileInputStream(new File(localPath))) {
            fileBytes = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error("Failed to read input stream from path: " + localPath, e);
            throw exception(ResultStatusEnum.ERROR.getStatus(),"上传文件io异常");
        }
        UploadEntity uploadEntity = this.uploadAndSave("excel", null, "download", fileName,null, fileBytes);
        return uploadEntity.getFileId();
    }

    @Override
    public String getUrl(SysUploadFileDto dto) {
        //获取url用的是指定的
        IUploadFileService uploadFileService = maps.get(dto.getStorage() + "UploadFileService");
        if (Objects.isNull(uploadFileService)){
            log.warn("未找到对应存储类型{}的实现",dto.getStorage());
            return null;
        }
        return uploadFileService.getUrl(dto);
    }

    @Override
    public String getThumbUrl(SysUploadFileDto dto) {
        //获取url用的是指定的
        IUploadFileService uploadFileService = maps.get(dto.getStorage() + "UploadFileService");
        if (Objects.isNull(uploadFileService)){
            log.warn("未找到对应存储类型{}的实现",dto.getStorage());
            return null;
        }
        return uploadFileService.getThumbUrl(dto);
    }

    @Override
    public List<SysUploadFileDto> findByWithUrl(String module, String bizNo) {
        if(StringUtils.isEmpty(bizNo)){
            return null;
        }
        List<SysUploadFileDto> list = sysUploadFileGateway.findListBy(SysUploadFileQueryContext.builder().module(module).bizNo(bizNo).build());
        List<SysUploadFileDto> rst = new ArrayList<>();
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        list.forEach(s -> {
            SysUploadFileDto dto = convert.toDto(s);
            dto.setUrl(getUrl(dto));
            rst.add(dto);
        });
        return rst;
    }
}
