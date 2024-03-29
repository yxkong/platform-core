package com.github.platform.core.sys.domain.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.sys.domain.dto.SysDeptDto;
import com.github.platform.core.sys.domain.dto.SysMenuDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 * 
 * @author yxkong
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeSelectDto implements Serializable {

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelectDto> children;



    public TreeSelectDto(SysDeptDto dept) {
        this.id = dept.getId();
        this.label = dept.getDeptName();
        if (CollectionUtil.isNotEmpty(dept.getChildren())){
            this.children = dept.getChildren().stream().map(TreeSelectDto::new).collect(Collectors.toList());
        } else {
            this.children = new ArrayList<>();
        }

    }

    public TreeSelectDto(SysMenuDto menu) {
        this.id = menu.getId();
        this.label = menu.getName();
        if (CollectionUtil.isNotEmpty(menu.getChildren())){
            this.children =  menu.getChildren().stream().map(TreeSelectDto::new).collect(Collectors.toList());
        } else {
            this.children = new ArrayList<>();
        }
    }
}
