package com.github.platform.core.file.infra.service;

import com.github.platform.core.file.domain.common.entity.SysUploadFileBase;
import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.standard.constant.SymbolConstant;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 文件服务
 * @author: yxkong
 * @date: 2022/8/2 4:53 PM
 * @version: 1.0
 */
public interface IUploadFileService {
    /**
     * 文件上传并保存数据库（建议用这个）
     * @param module 业务模块
     * @param bizNo  业务唯一标识
     * @param fileName  文件名称，带后缀
     * @param fileSize 文件大小
     * @param fileBytes 文件字节数组
     * @return 上传成功，并插入成功，才算成功，否则返回null
     */
    SysUploadFileDto uploadAndSave(String module,String bizNo, String fileName,Long fileSize,  byte[] fileBytes);
    /**
     * 文件上传（使用这个，需要自己记录是什么类型的存储方式）
     * @param module 模块名，以模块区分
     * @param bizNo 业务号
     * @param uploadFileName 上传的文件名称 带后缀
     * @param is 文件流
     */
    String upload(String module, String bizNo,  String uploadFileName, InputStream is);

    /**
     * 根据文件id获取浏览的url
     * @param dto
     * @return
     */
    String getUrl(SysUploadFileDto dto);
    /**
     * 根据文件id获取浏览的缩略图
     * @param dto
     * @return
     */
    String getThumbUrl(SysUploadFileDto dto);

    /**
     * 根据bizNo以及模块查询对应的所有记录
     * @param bizNo
     * @param module
     * @return
     */
    List<SysUploadFileBase> findBy(String bizNo, String module);

    /**
     /**
     * 获取存储的 objectName（全路径）和fileId
     * @param module  模块
     * @param datePath 时间路径
     * @param bizNo 业务编号
     * @param fileName 文件名称
     * @return
     */
    Pair<String,String> getObjectNameAndFileId(String module, String datePath, String bizNo, String fileName);

    default String getPath(String path){
        if (Objects.isNull(path)){
            return null;
        }
        return path.replace("\\", SymbolConstant.divide);
    }
    default String getDatePath(){
        LocalDateTime nowDate = LocalDateTime.now();
        //生成日期目录
        return LocalDateTimeUtil.dateTime(nowDate,"yyyyMMdd");
    }
}
