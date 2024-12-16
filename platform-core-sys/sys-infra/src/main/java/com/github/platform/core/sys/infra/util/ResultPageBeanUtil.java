package com.github.platform.core.sys.infra.util;

import com.github.pagehelper.PageInfo;
import com.github.platform.core.standard.entity.dto.PageBean;

import java.util.List;
import java.util.Objects;

/**
 *分页返回bean
 * @author: yxkong
 * @date: 2023/3/23 1:46 PM
 * @version: 1.0
 */
public class ResultPageBeanUtil {

    /**
     * 将PageInfo 转为pageBean
     * @param pageInfo
     * @return
     */
    public static PageBean  pageBean(PageInfo  pageInfo){
        if (Objects.isNull(pageInfo)){
            return null;
        }
        return PageBean.builder()
                .pages(pageInfo.getPages())
                .pageSize(pageInfo.getPageSize())
                .pageNum(pageInfo.getPageNum())
                .totalSize(pageInfo.getTotal())
                .data( pageInfo.getList())
                .build();
    }
    /**
     * 将PageInfo 转为pageBean
     * @param pageInfo 分页信息
     * @param data 自定义数据体
     * @return
     */
    public static PageBean pageBean(PageInfo pageInfo,List data){
        if (Objects.isNull(pageInfo)){
            return null;
        }
        return PageBean.builder()
                .pages(pageInfo.getPages())
                .pageSize(pageInfo.getPageSize())
                .pageNum(pageInfo.getPageNum())
                .totalSize(pageInfo.getTotal())
                .data(data)
                .build();
    }


}
