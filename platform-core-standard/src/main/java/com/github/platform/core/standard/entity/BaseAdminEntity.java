package com.github.platform.core.standard.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 后台管理实体
 * 已转移到 common模块 {@link com.github.platform.core.common.entity.BaseAdminEntity }  提供了id解密的能力
 * @author: yxkong
 * @date: 2023/8/3 3:18 PM
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Deprecated
public class BaseAdminEntity extends BaseEntity {
    @Schema(description = "加密id")
    protected transient String strId;
    /**该字段，只有页面查询*/
    @JsonIgnore
    @Schema(description = "数据权限")
    protected transient  String dataScope;
    /**创建人*/
    @Schema(description = "创建人")
    protected String createBy;
    /**更新人*/
    @Schema(description = "更新人")
    protected String updateBy;
    @Schema(description = "状态(1启用 0停用)")
    protected Integer status ;
    /**
     * 页码
     */
    @JsonIgnore
    @Schema(description = "页码",example = "1")
    protected transient Integer pageNum;

    /**
     * 每页条数
     */
    @JsonIgnore
    @Schema(description = "每页条数",example = "10")
    protected transient Integer pageSize;

    /**
     * 获取其实的偏移量
     * @return
     */
    @JsonIgnore
    public int getStartOffset(){
        if (Objects.nonNull(this.pageSize) && Objects.nonNull(this.pageSize)){
            return this.pageSize* (this.pageNum-1);
        }
       return 0;
    }


    @JsonIgnore
    @Schema(description = "开始时间")
    protected transient String searchStartTime;
    @JsonIgnore
    @Schema(description = "结束时间")
    protected transient String searchEndTime;

}
