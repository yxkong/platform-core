package com.github.platform.core.file.application.executor;

import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.file.domain.entity.UploadEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * 文件上传执行器
 *
 * @author: yxkong
 * @date: 2022/8/3 3:51 PM
 * @version: 1.0
 */
public interface IUploadFileExecutor {
    /**
     * 文件上传并保存到文件库里
     *   module+bizNo 能全局唯一，如果不加module，只有bizNo，不同业务可能重复
     * @param module 业务模块
     * @param bizNo  业务唯一标识
     * @param path 路径，建议用年月日来区分,为null，内部按年月日划分
     * @param fileName  文件名称，带后缀
     * @param is 文件流
     * @return 上传成功，并插入成功，才算成功，否则返回null
     */
    UploadEntity uploadAndSave(String module, String bizNo, String path, String fileName,Long fileSize, InputStream is);

    /**
     * 根据模块和业务编号删除对应的文件
     * @param module
     * @param bizNo
     */
    void deleteByBizNo(String module,String bizNo);


    /**
     * 上传Excel
     * @param fileName excel名称
     * @param localPath  文件本地地址
     */
    String uploadExcel(String fileName, String localPath) throws FileNotFoundException;

    /**
     * 根据存储方式以及文件id获取浏览的url
     * @param dto  不给存储方式，会根据fileId反查对应的类型
     * @return
     */
    String getUrl(SysUploadFileDto dto);

    /**
     * 获取缩略图
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
    List<SysUploadFileDto> findByWithUrl(String module, String bizNo );

}
