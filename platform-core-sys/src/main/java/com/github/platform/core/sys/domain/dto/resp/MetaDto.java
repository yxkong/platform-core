package com.github.platform.core.sys.domain.dto.resp;


import com.github.platform.core.sys.domain.constant.MenuConstant;
import com.github.platform.core.sys.domain.dto.SysMenuDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 路由显示信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaDto {
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 设置为true，则不会被 <keep-alive>缓存
     */
    private boolean noCache;

    /**
     * 内链地址（http(s)://开头）
     */
    private String link;

    public MetaDto(SysMenuDto menu){
        this.title = menu.getName();
        this.icon = menu.getIcon();
        this.noCache = MenuConstant.CACHE_YES.equals(menu.getCache());
        if (menu.isFrameUrl()){
            this.link = menu.getPath();
        }
    }

    public MetaDto(String name, String icon) {
        this.title = name;
        this.icon = icon;
    }
}
