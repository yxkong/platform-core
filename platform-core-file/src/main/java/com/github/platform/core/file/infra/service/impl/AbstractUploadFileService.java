package com.github.platform.core.file.infra.service.impl;

import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.utils.EncryptUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.file.domain.common.entity.SysUploadFileBase;
import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.file.infra.configuration.properties.UploadProperties;
import com.github.platform.core.file.infra.convert.SysUploadFileInfraConvert;
import com.github.platform.core.file.infra.service.IUploadFileService;
import com.github.platform.core.persistence.mapper.file.SysUploadFileMapper;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * 文件上传抽象服务
 *
 * @author: yxkong
 * @date: 2022/8/2 5:53 PM
 * @version: 1.0
 */
@Slf4j
public abstract class AbstractUploadFileService implements IUploadFileService {

    protected SysUploadFileMapper uploadFileMapper;
    protected UploadProperties properties;
    protected SysUploadFileInfraConvert convert;
    /**获取云的配置*/
    protected abstract UploadProperties.OssProperties getOssProperties();

    /**
     * 获cnamed的对应的t图片url
     * @param dto
     * @return
     */
    protected String getCnameUrl(SysUploadFileDto dto){
        if (StringUtils.isEmpty(getOssProperties().getCname())){
            return null;
        }
        //如果配置了cname，则直接返回cname的地址
        return getOssProperties().getCname() + SymbolConstant.divide + getOssProperties().getBucketName() +
                SymbolConstant.divide + dto.getFilePath();
    }
    protected String getThumbCnameUrl(String thumbUrl,String url){
        if (StringUtils.isEmpty(getOssProperties().getCname())){
            return url;
        }
        return url+ thumbUrl.substring(thumbUrl.indexOf(SymbolConstant.question));
    }

    @Override
    public SysUploadFileDto uploadAndSave(String module, String bizNo, String fileName, Long fileSize,  byte[] fileBytes) {
        String fileId = generateFieldId();
        String fileType = getFileType(fileName);
        // 计算 MD5 哈希值
        String fileHash = getFileHash(fileBytes);
        // 获取文件大小
        long actualFileSize = Objects.nonNull(fileSize) ? fileSize : fileBytes.length;
        // 获取上传文件名称
        String uploadFileName = getUploadFileName(fileId, fileType);
        // 上传文件到对应的位置
        String relativeFile;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes)) {
            relativeFile = this.upload(module, bizNo, uploadFileName, bis);
            if (log.isWarnEnabled()){
                log.warn("module:{} bizNo:{} fileName:{} relativeFile:{} uploadName:{}",module,bizNo,fileName,relativeFile,uploadFileName);
            }
        } catch (Exception e) {
            log.error("Failed to upload file to OSS.", e);
            return null;
        }
        //记录上传日志
        SysUploadFileBase record = SysUploadFileBase.builder()
                .module(module)
                .bizNo(bizNo)
                .fileName(fileName)
                .filePath(relativeFile)
                .fileId(fileId)
                .fileHash(fileHash)
                .fileType(fileType)
                .fileSize(actualFileSize)
                .storage(properties.getStorage().name())
                .tenantId(LoginUserInfoUtil.getTenantId())
                .createBy(LoginUserInfoUtil.getLoginName())
                .createTime(LocalDateTimeUtil.dateTime())
                .build();
        int num = uploadFileMapper.insert(record);
        if (num <= 0) {
            return null;
        }
        // 关闭 ByteArrayInputStream
        return convert.toDto(record);
    }

    private String getFileHash(byte[] fileBytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(fileBytes)) {
            return EncryptUtil.getInstance().md5FileHash(bis);
        } catch (Exception e) {
            log.error("Failed to calculate file hash.", e);
        }
        return null;
    }

    @Override
    public List<SysUploadFileBase> findBy(String bizNo, String module) {
        if (StringUtils.isEmpty(bizNo)) {
            return null;
        }
        SysUploadFileBase record = SysUploadFileBase.builder().bizNo(bizNo).build();
        if (StringUtils.isNotEmpty(module)) {
            record.setModule(module);
        }
        return uploadFileMapper.findListBy(record);
    }
    @Override
    public Pair<String,String> getObjectNameAndFileId(String module, String datePath, String bizNo, String fileName){
        String fileId = generateFieldId();
        String objectName = getFilePath(module, datePath, bizNo).append(File.separator).append(fileId).append(SymbolConstant.period).append(getFileType(fileName)).toString();
        return Pair.of(objectName,fileId);
    }

    @Override
    public String getThumbUrl(SysUploadFileDto dto) {
        return null;
    }

    /**
     * 获取文件路径
     * @param module
     * @param datePath
     * @param bizNo
     * @return
     */
    protected StringBuffer getFilePath(String module,String datePath,String bizNo){
        StringBuffer sb  = new StringBuffer(module);
        sb.append(File.separator).append(datePath);
        if (StringUtils.isNotEmpty(bizNo)){
            sb.append(File.separator).append(bizNo);
        }
        return sb;
    }
    /**
     * 获取文件路径
     * @param module
     * @param datePath
     * @param bizNo
     * @return
     */
    protected String getObjectName(String module,String datePath,String bizNo,String uploadFileName){
        StringBuilder sb  = new StringBuilder(module);
        sb.append(SymbolConstant.divide).append(datePath);
        if (StringUtils.isNotEmpty(bizNo)){
            sb.append(SymbolConstant.divide).append(bizNo);
        }
        return sb.append(SymbolConstant.divide).append(uploadFileName).toString();
    }




    /**
     * 生成唯一id
     * @return uuid
     */
    protected String generateFieldId() {
        return StringUtils.uuidRmLine();
    }

    /**
     * 获取文件类型
     * @param fileName
     * @return
     */
    protected String getFileType(String fileName){
        return fileName.substring(fileName.lastIndexOf(".")+1 );
    }

    /**
     * 获取上传文件的名称
     * @param fileId
     * @param fileType
     * @return
     */
    protected String getUploadFileName(String fileId,String fileType){
        return fileId + SymbolConstant.period + fileType;
    }

    protected String getObjectName(String path,String uploadFileName){
        return path + SymbolConstant.divide + uploadFileName;
    }
}
