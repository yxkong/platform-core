package com.github.platform.core.sys.domain.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.platform.core.common.entity.BaseAdminEntity;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.sys.domain.constant.MenuConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
* 系统菜单模型实体
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:05.830
* @version 1.0
*/
@Data
@SuperBuilder
@NoArgsConstructor
public class SysMenuBase extends BaseAdminEntity   {
    /** 菜单名称 */
    @Schema(description = "菜单名称")
    protected String name;
    /** 父菜单ID */
    @NotNull(message="父菜单ID（parentId）不能为空")
    @Schema(description = "父菜单ID")
    protected Long parentId;
    /** 显示顺序 */
    @Schema(description = "显示顺序")
    protected Integer sort;
    /** 路由地址 */
    @Schema(description = "路由地址")
    protected String path;
    /** 组件路径 */
    @Schema(description = "组件路径")
    protected String component;
    /** 路由参数 */
    @Schema(description = "路由参数")
    protected String query;
    /** 外链状态（0外链 1非外链） */
    @Schema(description = "外链状态（0外链 1非外链）")
    @NotNull(message="外链状态（0外链 1非外链）（frame）不能为空")
    protected Integer frame;
    /** 缓存状态（0缓存 1不缓存） */
    @Schema(description = "缓存状态（0缓存 1不缓存）")
    protected Integer cache;
    /** 菜单类型（M目录 C菜单 F按钮） */
    @Schema(description = "菜单类型（M目录 C菜单 F按钮）")
    @NotEmpty(message="菜单类型（M目录 C菜单 F按钮）（type）不能为空")
    protected String type;
    /** 菜单状态（0显示 1隐藏） */
    @Schema(description = "菜单状态（0显示 1隐藏）")
    @NotNull(message="菜单状态（0显示 1隐藏）（visible）不能为空")
    protected Integer visible;
    /** 赋予租户状态(0 不赋予,1 赋予) */
    @Schema(description = "赋予租户状态(0 不赋予,1 赋予)")
    @NotNull(message="赋予租户状态(0 不赋予,1 赋予)（giveTenant）不能为空")
    protected Integer giveTenant;
    /** 权限标识 */
    @Schema(description = "权限标识")
    protected String perms;
    /** 菜单图标 */
    @Schema(description = "菜单图标")
    protected String icon;


    @JsonIgnore
    public boolean isGiveTenantMenu(){
        return MenuConstant.GIVE_TENANT_YES.equals(this.giveTenant);
    }
    @JsonIgnore
    public Boolean isMenuTree(){
        return isMenu() || isDir();
    }
    @JsonIgnore
    public Boolean isSelectShow(){
        return isApp() || isMenu() || isDir();
    }
    @JsonIgnore
    public boolean isApp(){
        return MenuConstant.TYPE_APP.equals(this.getType());
    }
    /**
     * 是否菜单
     * @return
     */
    @JsonIgnore
    public boolean isMenu(){
        return MenuConstant.TYPE_MENU.equals(this.getType());
    }

    /**
     * 是否目录
     * @return
     */
    @JsonIgnore
    public boolean isDir(){
        return MenuConstant.TYPE_DIR.equals(this.getType());
    }


    /**
     * 是否按钮
     * @return
     */
    @JsonIgnore
    public boolean isBtn(){
        return MenuConstant.TYPE_BUTTON.equals(this.getType());
    }
    /**
     * 是否接口
     * @return
     */
    @JsonIgnore
    public boolean isInterface(){
        return MenuConstant.TYPE_INTERFACE.equals(this.getType());
    }

    /**
     * 是否为根目录
     * @return
     */
    @JsonIgnore
    public boolean isRoot() {
        return  MenuConstant.ROOT_ID.equals(this.getParentId()) ;
    }
    @JsonIgnore
    public boolean isRootDir() {
        return  isRoot() && isDir() ;
    }
    @JsonIgnore
    public boolean isRootMenu() {
        return  isRoot() && isMenu() ;
    }
    /**
     * 是否为外链
     * @return 结果
     */
    @JsonIgnore
    public boolean isFrameUrl() {
        return MenuConstant.FRAME_YES.equals(this.getFrame());
    }
    @JsonIgnore
    public boolean isInnerLink() {
        return MenuConstant.FRAME_NO.equals(this.getFrame()) && StringUtils.ishttp(this.getPath());
    }
}
