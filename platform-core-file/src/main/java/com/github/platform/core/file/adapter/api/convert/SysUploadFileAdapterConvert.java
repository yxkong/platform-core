package com.github.platform.core.file.adapter.api.convert;

import com.github.platform.core.file.adapter.api.command.SysUploadFileCmd;
import com.github.platform.core.file.adapter.api.command.SysUploadFileQuery;
import com.github.platform.core.file.domain.context.SysUploadFileContext;
import com.github.platform.core.file.domain.context.SysUploadFileQueryContext;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 上传文件表Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-14 18:22:38.776
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysUploadFileAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysUploadFileQueryContext toQuery(SysUploadFileQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysUploadFileContext toContext(SysUploadFileCmd cmd);
}