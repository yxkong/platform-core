package com.github.platform.core.file.application.executor;

import com.github.platform.core.file.domain.context.SysUploadFileContext;
import com.github.platform.core.file.domain.context.SysUploadFileQueryContext;
import com.github.platform.core.file.domain.dto.SysUploadFileDto;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.dto.ResultBean;

/**
 * 文件上传处理器
 * @author: yxkong
 * @date: 2023/12/27 15:47
 * @version: 1.0
 */
public interface ISysUploadFileExecutor {
    /**
     * 查询上传文件表列表
     *
     * @param context 查询上下文
     * @return 分页结果
     */
    PageBean<SysUploadFileDto> query(SysUploadFileQueryContext context);

    /**
     * 新增上传文件表
     *
     * @param context 新增上下文
     * @return 操作结果
     */
    void insert(SysUploadFileContext context);

    /**
     * 根据id查询上传文件表明细
     *
     * @param id 主键
     * @return 单条记录
     */
    SysUploadFileDto findById(Long id);

    /**
     * 修改上传文件表
     *
     * @param context 更新上下文
     * @return 更新结果
     */
    void update(SysUploadFileContext context);

    /**
     * 根据id删除上传文件表记录
     *
     * @param id 主键
     * @return 删除结果
     */
    void delete(Long id);
}
