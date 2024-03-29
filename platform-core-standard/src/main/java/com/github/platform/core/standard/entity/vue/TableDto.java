package com.github.platform.core.standard.entity.vue;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 表个数据
 * @author: yxkong
 * @date: 2024/3/6 13:31
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableDto {
    @Schema(description = "表格头")
    private List<TableTitle> titles;
    @Schema(description = "数据")
    private List<Map<String,Object>> dataList;

    public void addTitle(String prop,String lable,Integer width){
        if (Objects.isNull(this.titles)){
            this.titles = new ArrayList<>();
        }
        TableTitle tableTitle = this.titles.stream().filter(s -> s.getProp().equals(prop)).findAny().orElse(null);
        if (Objects.nonNull(tableTitle)){
            return;
        }
        this.titles.add(new TableTitle(prop,lable,width));
    }
    public void addData(Map<String,Object> data){
        if (Objects.isNull(this.dataList)){
            this.dataList = new ArrayList<>();
        }
        this.dataList.add(data);
    }
}
