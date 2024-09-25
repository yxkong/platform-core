package com.github.platform.core.file.infra.service.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.file.infra.configuration.properties.UploadProperties;
import com.github.platform.core.file.infra.convert.SysUploadFileInfraConvert;
import com.github.platform.core.persistence.mapper.file.SysUploadFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Objects;

/**
 * 天翼云文件上传
 *
 * @author: yxkong
 * @date: 2022/8/2 5:53 PM
 * @version: 1.0
 */
@Slf4j
public class CTYunFileUploadService extends AbstractUploadFileService  {

    private AmazonS3 oosClient;
    public CTYunFileUploadService(AmazonS3 oosClient, SysUploadFileMapper uploadFileMapper, UploadProperties properties, SysUploadFileInfraConvert convert) {
        this.uploadFileMapper = uploadFileMapper;
        this.properties = properties;
        this.oosClient = oosClient;
        this.convert = convert;
    }

    @Override
    protected UploadProperties.OssProperties getOssProperties() {
        return this.properties.getCtyun();
    }

    /**
     * 文件上传
     *
     * @param module   模块名，以模块区分
     * @param bizNo    业务号
     * @param uploadFileName 文件名称 带后缀
     * @param is       文件流
     */
    @Override
    public String upload(String module, String bizNo, final String uploadFileName, InputStream is) {
        try {
            String objectName = getObjectName(module, getDatePath(), bizNo,uploadFileName);
            PutObjectRequest request = new PutObjectRequest(getOssProperties().getBucketName(),objectName, is, new ObjectMetadata());
            oosClient.putObject(request);
            return objectName;
        } catch (AmazonServiceException as){
            log.error("上传天翼云oss服务端异常，code:{},message:{},requestId:{}",as.getErrorCode(),as.getMessage(),as.getRequestId());
        } catch (AmazonClientException ce) {
            log.error("上传天翼云oss客户端异常,message:{}",ce.getMessage());
        }
        return null;
    }


    @Override
    public String getUrl(SysUploadFileDto dto) {
        String cnameUrl = getCnameUrl(dto);
        if (StringUtils.isNotEmpty(cnameUrl)){
            return cnameUrl;
        }
        String bucketName = getOssProperties().getBucketName();
        GeneratePresignedUrlRequest shareUrlRequest = new GeneratePresignedUrlRequest(bucketName,dto.getFilePath());

        // 失效时间如果没有配置，则默认30分钟
        int activeMinutes = Objects.isNull(getOssProperties().getLinkExpireMinutes()) ? 30 : getOssProperties().getLinkExpireMinutes();
        shareUrlRequest.setExpiration(DateUtils.addMinutes(new Date(), activeMinutes));
        URL url = oosClient.generatePresignedUrl(shareUrlRequest);
        return url.toString();
    }
}