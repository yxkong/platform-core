package com.github.platform.core.file.infra.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
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
 * 阿里云文件上传
 * @author: yxkong
 * @date: 2024/3/22 15:12
 * @version: 1.0
 */
@Slf4j
public class AliyunFileUploadFileService extends AbstractUploadFileService{
    private OSS ossClient;
    public AliyunFileUploadFileService(OSS ossClient, SysUploadFileMapper uploadFileMapper, UploadProperties properties, SysUploadFileInfraConvert convert) {
        this.uploadFileMapper = uploadFileMapper;
        this.properties = properties;
        this.ossClient = ossClient;
        this.convert = convert;
    }
    private UploadProperties.OssProperties getAliyun(){
        return properties.getAliyun();
    }
    @Override
    public void upload(String module, String bizNo, String path, String uploadFileName, InputStream is) {

        try {
            PutObjectRequest putObjectRequest = new PutObjectRequest(getAliyun().getBucketName(), getObjectName(path,uploadFileName), is);
            // 创建PutObject请求。
            PutObjectResult result = ossClient.putObject(putObjectRequest);
        }catch (OSSException oe) {
            log.error("上传阿里云oss服务端异常，code:{},message:{},requestId:{},hostId:{}",oe.getErrorCode(),oe.getMessage(),oe.getRequestId(),oe.getHostId());
        } catch (ClientException ce) {
            log.error("上传阿里云oss客户端异常,code:{},message:{},requestId:{}",ce.getErrorCode(),ce.getMessage(),ce.getRequestId());
        }
    }

    @Override
    public String getUrl(SysUploadFileDto dto) {
        return getUrlStr(dto,null);
    }

    /**
     *
     * @param dto
     * @param style style = "image/resize,m_fixed,w_100,h_100/rotate,90";
     *  可以实时处理图片 将图片缩放为固定宽高100 px后，再旋转90°。
     * @return
     */
    private String getUrlStr(SysUploadFileDto dto,String style) {
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(getAliyun().getBucketName(), dto.getObjectName());
        // 设置失效时间
        int activeMinutes = Objects.equals(dto.getPermanent() ,Boolean.TRUE) ? Integer.MAX_VALUE :
                Objects.isNull(getAliyun().getLinkExpireMinutes()) ? 30 : getAliyun().getLinkExpireMinutes();
        req.setExpiration(DateUtils.addDays(new Date(), activeMinutes));
        if (StringUtils.isNotEmpty(style)){
            req.setProcess(style);
        }
        URL url  = ossClient.generatePresignedUrl(req);
        return url.toString();
    }

    @Override
    public String getThumbUrl(SysUploadFileDto dto) {
        if (!dto.isImage()){
            return null;
        }
        /**
         *  m_lfit 等比例缩放
         */
        String style = "image/resize,m_lfit,w_100,h_100";
        return getUrlStr(dto,style);
    }
}
