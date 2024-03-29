package com.github.platform.core.standard.entity.vue;

import lombok.Data;
/**
 * 表格表格title
 * @author: yxkong
 * @date: 2023/7/13 4:37 PM
 * @version: 1.0
 */
@Data
public class TableTitle {

    private String label;
    private String prop;
    private Integer width;

    public TableTitle(String prop,Integer width) {
        this.prop = prop;
        this.label = prop;
        this.width = width;
    }
    public TableTitle(String prop,String label,Integer width) {
        this.prop = prop;
        this.label = label;
        this.width = width;
    }
}
