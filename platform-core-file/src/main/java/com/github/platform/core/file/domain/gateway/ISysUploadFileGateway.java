package com.github.platform.core.file.domain.gateway;

import com.github.platform.core.file.domain.common.entity.SysUploadFileBase;
import com.github.platform.core.file.domain.context.SysUploadFileContext;
import com.github.platform.core.file.domain.context.SysUploadFileQueryContext;
import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

/**
* 上传文件表网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
public interface ISysUploadFileGateway {
    /**
    * 查询上传文件表列表
    * @param context 查询上下文
    * @return 分页结果
    */
    PageBean<SysUploadFileDto> query(SysUploadFileQueryContext context);

    /**
     * 查询上传列表
     * @param context
     * @return
     */
    List<SysUploadFileDto> findListBy(SysUploadFileQueryContext context);
    /**
    * 新增上传文件表
    * @param context 新增上下文
    * @return 返回结果
    */
    SysUploadFileDto insert(SysUploadFileContext context);
    /**
    * 根据id查询上传文件表明细
    * @param id 主键
    * @return 结果
    */
    SysUploadFileDto findById(Long id);
    /**
    * 修改上传文件表
    * @param context 修改上下文
    * @return 更新结果
    */
    Pair<Boolean, String> update(SysUploadFileContext context);
    /**
    * 根据id删除上传文件表记录
    * @param id 主键id
    * @return 删除结果
    */
    Pair<Boolean, String> delete(Long id);


}
