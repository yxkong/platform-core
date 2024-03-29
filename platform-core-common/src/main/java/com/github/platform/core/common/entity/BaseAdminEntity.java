package com.github.platform.core.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.SignUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 带加密id的
 * 使用这个的时候，如果需要设置id为null，需要将strId也设置为null
 * @author: yxkong
 * @date: 2023/11/3 11:56
 * @version: 1.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BaseAdminEntity extends BaseEntity {
    private static final long serialVersionUID = 8418951546982554345L;
    /**该字段，只有页面查询*/
    @JsonIgnore
    @Schema(description = "数据权限")
    protected String dataScope;
    /**该字段，只有页面查询*/
    @JsonIgnore
    @Schema(description = "排序字段")
    protected transient String orderStr;
    /**创建人*/
    @Schema(description = "创建人")
    protected String createBy;
    /**更新人*/
    @Schema(description = "更新人")
    protected String updateBy;
    @Schema(description = "状态(1启用 0停用)")
    protected Integer status ;

    @JsonIgnore
    @Schema(description = "开始时间")
    protected String searchStartTime;
    @JsonIgnore
    @Schema(description = "结束时间")
    protected String searchEndTime;
    @JsonIgnore
    @Schema(description = "扩展参数")
    private Map<String, Object> params;

    public void addParam(String key,Object val){
        if (null == this.params){
            this.params = new HashMap<>();
        }
        this.params.put(key,val);
    }

    @Override
    public Long getId() {
        if (Objects.nonNull(this.id)){
            return this.id;
        }
        if (StringUtils.isNotEmpty(this.strId)){
            return SignUtil.getLongId(this.strId);
        }
        return super.getId();
    }
    /**
     * 设置主键id为null
     */
    public void setIdNull() {
        this.strId = null;
        this.id = null;
    }
    @JsonIgnore
    public boolean isOn(){
        return StatusEnum.ON.getStatus().equals(this.status) ;
    }
    @JsonIgnore
    public boolean isOff(){
        return StatusEnum.OFF.getStatus().equals(this.status) ;
    }
    public boolean statusIsNull(){
        return Objects.isNull(this.status);
    }


}