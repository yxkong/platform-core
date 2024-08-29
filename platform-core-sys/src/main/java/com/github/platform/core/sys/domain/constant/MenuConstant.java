package com.github.platform.core.sys.domain.constant;

import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;

/**
 * 菜单常量
 * @author: yxkong
 * @date: 2023/4/9 4:17 下午
 * @version: 1.0
 */
public interface MenuConstant {
    /**一级菜单*/
    Long ROOT_ID = 0L;
    /**子菜单（1子菜单 0主菜单）*/
    Integer SUB_MENU_YES = StatusEnum.ON.getStatus();
    Integer SUB_MENU_NO = StatusEnum.OFF.getStatus();
    /**显示状态：1显示*/
    Integer VISIBLE_YES = StatusEnum.ON.getStatus();
    /**显示状态：0隐藏*/
    Integer VISIBLE_NO =  StatusEnum.OFF.getStatus();
    /**赋予租户状态：1是*/
    Integer GIVE_TENANT_YES = StatusEnum.ON.getStatus();
    /**赋予租户状态：0否*/
    Integer GIVE_TENANT_NO = StatusEnum.OFF.getStatus();
    /** 外链状态:1是 */
    Integer FRAME_YES = StatusEnum.ON.getStatus();
    /** 外链状态:0否 */
    Integer FRAME_NO = StatusEnum.OFF.getStatus();
    /** 缓存状态：1是*/
    Integer CACHE_YES =  StatusEnum.ON.getStatus();
    /** 缓存状态：0否*/
    Integer CACHE_NO = StatusEnum.OFF.getStatus();
    /** 菜单类型：应用 A */
    String TYPE_APP = "A";

    /**菜单类型： 菜单 M*/
    String TYPE_MENU = "M";
    /** 菜单类型：按钮 B */
    String TYPE_BUTTON = "B";
    /** 菜单类型：目录 D */
    String TYPE_DIR = "D";
    /** 菜单类型：接口 I */
    String TYPE_INTERFACE = "I";

    String NO_REDIRECT = "noRedirect";

    /** Layout组件标识 */
    String LAYOUT = "Layout";

    /** ParentView组件标识 */
    String PARENT_VIEW = "ParentView";

    /** InnerLink，在当前视窗打开 */
    String INNER_LINK = "InnerLink";
}
