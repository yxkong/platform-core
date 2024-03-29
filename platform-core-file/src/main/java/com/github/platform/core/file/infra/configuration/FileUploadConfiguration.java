package com.github.platform.core.file.infra.configuration;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.github.platform.core.common.annotation.ConditionalOnPropertyInList;
import com.github.platform.core.common.constant.PropertyConstant;
import com.github.platform.core.file.infra.configuration.properties.UploadProperties;
import com.github.platform.core.file.infra.convert.SysUploadFileInfraConvert;
import com.github.platform.core.file.infra.service.IUploadFileService;
import com.github.platform.core.file.infra.service.impl.AliyunFileUploadFileService;
import com.github.platform.core.file.infra.service.impl.CTYunFileUploadService;
import com.github.platform.core.file.infra.service.impl.DiskUploadFileService;
import com.github.platform.core.persistence.mapper.file.SysUploadFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 文件上传配置
 * @author: yxkong
 * @date: 2023/2/17 10:44 AM
 * @version: 1.0
 */
@Configuration
@Slf4j
public class FileUploadConfiguration {
    public static final String DISK_IUPLOAD_BEAN_NAME = "diskUploadFileService";
    public static final String CTYUN_IUPLOAD_BEAN_NAME = "ctyunUploadFileService";
    public static final String ALIYUN_FILE_IUPLOAD_BEAN_NAME = "aliyunUploadFileService";

    @Resource
    private SysUploadFileMapper uploadFileMapper;
    @Resource
    private UploadProperties properties;
    @Resource
    private SysUploadFileInfraConvert convert;

    /**
     * 阿里云实现
     * @return
     */
    @Bean(ALIYUN_FILE_IUPLOAD_BEAN_NAME)
    @ConditionalOnClass(OSS.class)
    @ConditionalOnPropertyInList(name = PropertyConstant.CON_FILE_ROUTERS, havingValue = "aliyun")
    public IUploadFileService aliyunUpload(){
        UploadProperties.OssProperties oss = properties.getAliyun();
        if (Objects.isNull(oss) || Objects.isNull(oss.getAccessId())){
            log.error("file.aliyun 参数未配置,请检查相关配置");
            throw new RuntimeException("请配置file.aliyun操作文件的相关的参数");
        }
        OSS ossClient =  new OSSClientBuilder().build(oss.getEndpoint(), oss.getAccessId(), oss.getAccessKey());
        return new AliyunFileUploadFileService(ossClient,uploadFileMapper,properties,convert);
    }

    /**
     * 天翼云实现
     * @return
     */
    @Bean(CTYUN_IUPLOAD_BEAN_NAME)
    @ConditionalOnClass(AmazonS3.class)
    @ConditionalOnPropertyInList(name = PropertyConstant.CON_FILE_ROUTERS, havingValue = "ctyun")
    public IUploadFileService ctyunUpload(){
        UploadProperties.OssProperties oss = properties.getCtyun();
        if (Objects.isNull(oss) || Objects.isNull(oss.getAccessId())){
            log.error("file.ctyun 参数未配置,请检查相关配置");
            throw new RuntimeException("请配置file.ctyun操作文件的相关的参数");
        }
        AmazonS3 oosClient = getAmazonS3(properties.getCtyun());
        return new CTYunFileUploadService(oosClient,uploadFileMapper,properties,convert);
    }
    /**
     * 磁盘实现
     * @return
     */
    @Bean(DISK_IUPLOAD_BEAN_NAME)
    @ConditionalOnPropertyInList(name = PropertyConstant.CON_FILE_ROUTERS, havingValue = "disk")
    public IUploadFileService diskUpload(){
        UploadProperties.DiskProperties disk = properties.getDisk();
        if (Objects.isNull(disk) || Objects.isNull(disk.getDiskPath())){
            log.error("file.disk 相关参数未配置,请检查相关配置");
            throw new RuntimeException("请配置file.disk操作文件的相关的参数");
        }
        return new DiskUploadFileService(uploadFileMapper,properties,convert);
    }
    private AmazonS3 getAmazonS3(UploadProperties.OssProperties cty) {
        ClientConfiguration clientConfig = new ClientConfiguration();
        //设置连接的超时时间，单位毫秒
        clientConfig.setConnectionTimeout(cty.getConnectionTimeout());
        //设置 socket 超时时间，单位毫秒
        clientConfig.setSocketTimeout(cty.getSocketTimeout());
        //设置 http
        clientConfig.setProtocol(Protocol.HTTP);
        //设置 V4 签名算法中负载是否参与签名，关于签名部分请参看《OOS 开发者文档》
        S3ClientOptions options = new S3ClientOptions();
        options.setPayloadSigningEnabled(true);
        // 创建 client
        AmazonS3 oosClient = new AmazonS3Client(
                new PropertiesCredentials(cty.getAccessId(),
                        cty.getAccessKey()), clientConfig);
        // 设置 endpoint
        oosClient.setEndpoint(cty.getEndpoint());
        //设置选项
        oosClient.setS3ClientOptions(options);
        return oosClient;
    }
}
