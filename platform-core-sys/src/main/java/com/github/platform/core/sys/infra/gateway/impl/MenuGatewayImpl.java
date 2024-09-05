package com.github.platform.core.sys.infra.gateway.impl;

import com.github.platform.core.auth.constant.RoleConstant;
import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.mapper.sys.SysMenuMapper;
import com.github.platform.core.persistence.mapper.sys.SysRoleMapper;
import com.github.platform.core.standard.constant.ResultStatusEnum;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.sys.domain.common.entity.SysMenuBase;
import com.github.platform.core.sys.domain.common.entity.SysRoleBase;
import com.github.platform.core.sys.domain.constant.Constants;
import com.github.platform.core.sys.domain.constant.MenuConstant;
import com.github.platform.core.sys.domain.context.SysMenuContext;
import com.github.platform.core.sys.domain.context.SysMenuQueryContext;
import com.github.platform.core.sys.domain.dto.SysMenuDto;
import com.github.platform.core.sys.domain.dto.resp.MetaDto;
import com.github.platform.core.sys.domain.dto.resp.RouterDto;
import com.github.platform.core.sys.domain.dto.resp.TreeSelectDto;
import com.github.platform.core.sys.domain.gateway.ISysMenuGateway;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.convert.SysMenuInfraConvert;
import com.github.platform.core.sys.infra.convert.SysRoleInfraConvert;
import com.github.platform.core.sys.infra.service.sys.ISysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单网关
 *
 * @author: yxkong
 * @date: 2023/1/4 4:04 PM
 * @version: 1.0
 */
@Service
@Slf4j
public class MenuGatewayImpl extends BaseGatewayImpl implements ISysMenuGateway {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleInfraConvert roleInfraConvert;
    @Resource
    private SysMenuInfraConvert infraConvert;
    @Resource
    private ISysRoleMenuService sysRoleMenuService;


    @Override
    public List<RouterDto> getRouters() {

        List<SysMenuBase> list = null;
        //判断是否为超级管理员
        if (AuthUtil.isSuperAdmin()) {
            list = sysMenuMapper.findMenuAll();
        } else {
            list = findUsableMenu(LoginUserInfoUtil.getUserId(),MenuConstant.TYPE_MENU);
            //将所有隐藏菜单查出
            list.addAll(findAllUnVisibleMenu());
        }
        List<SysMenuDto> menus = infraConvert.toDtos(list);
        /**
         * 这里过滤掉了应用，如果需要显示应用，需要处理
         */
        menus = menus.stream().filter(s -> s.isMenuTree()).collect(Collectors.toList());
        List<SysMenuDto> menuTree = buildMenuTree(menus);
        return buildRouters(menuTree);
    }
    private List<SysMenuBase> findUsableMenu(Long userId,String flag) {
        SysMenuBase sysMenuBase = SysMenuBase.builder().status( StatusEnum.ON.getStatus()).build();
        return sysMenuMapper.findMenuByUserId(userId,flag,sysMenuBase);
    }
    private List<SysMenuBase> findAllUnVisibleMenu() {
        SysMenuBase sysMenu = SysMenuBase.builder().visible(MenuConstant.VISIBLE_NO).type(MenuConstant.TYPE_MENU).build();
        return sysMenuMapper.findListBy(sysMenu);
    }
    @Override
    public List<TreeSelectDto> findUserMenuTree(Boolean hidden) {
        List<SysMenuBase> list = null;
        //判断是否为管理员
        if (AuthUtil.isSuperAdmin()) {
            if (hidden){
                list = sysMenuMapper.findMenuAll();
            } else  {
                list = sysMenuMapper.findListBy(SysMenuBase.builder().status(StatusEnum.ON.getStatus()).build());
            }
        } else {
            String flag = hidden ? MenuConstant.TYPE_MENU : null;
            list = this.findUsableMenu(LoginUserInfoUtil.getUserId(),flag);
        }
        List<SysMenuDto> menus = infraConvert.toDtos(list);
        return buildMenuTreeSelect(menus);
    }

    @Override
    public List<SysMenuDto> findMenuByRoleIds(List<Long> roleIds) {
        if (CollectionUtil.isEmpty(roleIds)){
            return null;
        }
        SysMenuBase sysMenuBase = SysMenuBase.builder().status( StatusEnum.ON.getStatus()).build();
        return infraConvert.toDtos(sysMenuMapper.findMenuByRoleIds(roleIds, sysMenuBase));
    }

    @Override
    public List<SysMenuDto> findByTenantId(Integer tenantId) {
        SysMenuBase sysMenuBase = SysMenuBase.builder().tenantId(tenantId).status( StatusEnum.ON.getStatus()).build();
        return infraConvert.toDtos(sysMenuMapper.findListBy(sysMenuBase));
    }

    /**
     * 构建前端路由所需要的菜单
     *   到这一步已经把菜单树化了
     * @param menuDtos
     * @return
     */
    private List<RouterDto> buildRouters(List<SysMenuDto> menuDtos) {
        List<RouterDto> routers = new LinkedList<RouterDto>();
        for (SysMenuDto menu : menuDtos) {
            RouterDto router =RouterDto.builder()
                    .hidden(MenuConstant.VISIBLE_NO.equals(menu.getVisible()))
                    .name(StringUtils.capitalize(menu.getPath()))
                    .component(MenuConstant.LAYOUT)
                    .path(getRouterPath(menu))
                    .query(menu.getQuery())
                    .meta(new MetaDto(menu))
                    .build();
            //是目录，直接默认
            if (menu.isDir() || menu.isApp()){
                router.setAlwaysShow(true);
                router.setRedirect(MenuConstant.NO_REDIRECT);
            } else if (menu.isMenu()){
                //菜单有组件路径
                router.setComponent(menu.getComponent());
            } else if (menu.isInnerLink()){
                //菜单内链地址
                router.setPath("/");
                List<RouterDto> childrenList = new ArrayList<>();
                RouterDto children = new RouterDto();
                String routerPath = innerLinkReplaceEach(menu.getPath());
                children.setPath(routerPath);
                children.setComponent(MenuConstant.INNER_LINK);
                children.setName(StringUtils.capitalize(routerPath));
                children.setMeta(new MetaDto(menu.getName(), menu.getIcon()));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            if (!menu.isTopMenu()){
                router.setComponent(null);
            }
            //有子节点
            if (CollectionUtil.isNotEmpty(menu.getChildren()) ) {
                //如果有子节点不是目录
                if (!(menu.isDir() || menu.isApp())){
                    continue;
                }
                //有menu的时候
//                if (menu.isMenu()&& hashMenu(menu.getChildren())){
//                    router.setRedirect(MenuConstant.NO_REDIRECT);
//                    router.setComponent(MenuConstant.PARENT_VIEW);
//                }
                //递归处理子节点
                router.setChildren(buildRouters(menu.getChildren()));
            } else {
                router.setAlwaysShow(false);
            }
            routers.add(router);
        }
        return routers;
    }

    private boolean hashMenu(List<SysMenuDto> list){
        return list.stream().anyMatch(SysMenuBase::isMenu);
    }

//    /**
//     * 构建菜单，只有有子节点，且菜单类型是目录才会执行
//     * @param menu
//     * @param router
//     */
//    private void processDir(MenuDto menu, RouterDto router){
//        if (!CollectionUtil.isEmpty(menu.getChildren()) && MenuConstant.TYPE_DIR.equals(menu.getType())) {
//            router.setAlwaysShow(true);
//            router.setComponent(MenuConstant.LAYOUT);
//            router.setRedirect(MenuConstant.NO_REDIRECT);
//            //递归处理子菜单
//            router.setChildren(buildRouters(menu.getChildren()));
//        }
//    }
//    private RouterDto buildMenuBase(MenuDto menu){
//        return RouterDto.builder()
//                .hidden(MenuConstant.VISIBLE_NO.equals(menu.getVisible()))
//                .name(getRouteName(menu))
//                .component(getComponent(menu))
//                .path(getRouterPath(menu))
//                .query(menu.getQuery())
//                .meta(new MetaDto(menu))
//                .build();
//    }

    /**
     * 构建查询菜单树
     * @param menuList
     * @return
     */
    private List<TreeSelectDto> buildMenuTreeSelect(List<SysMenuDto> menuList) {
        List<SysMenuDto> menuTrees = buildMenuTree(menuList);
        return menuTrees.stream().map(TreeSelectDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(SysMenuContext context) {
        SysMenuBase sysMenuBase = infraConvert.toSysMenuBase(context);
        if (Objects.isNull(sysMenuBase.getParentId())) {
            //如果parentId为空，则代表创建1级菜单    需要手动设置为0
            sysMenuBase.setParentId(MenuConstant.ROOT_ID);
        }
        int row = sysMenuMapper.insert(sysMenuBase);
        if ( row <= 0) {
            throw exception(ResultStatusEnum.COMMON_INSERT_ERROR);
        }
        //添加角色菜单关联信息
        insertRoleMenu(context, sysMenuBase.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void reloadPermission() {
        //查询出所有可以给租户管理员的菜单
        List<Long> menuIds = sysMenuMapper.findAllMenuIds(MenuConstant.GIVE_TENANT_YES);
        if (CollectionUtil.isEmpty(menuIds)) {
            throw exception(SysInfraResultEnum.MENU_RELOAD_PERMISSION_EMPTY);
        }
        //清空租户管理员所有权限数据
        sysRoleMenuService.deleteByRoleKey(RoleConstant.TENANT_ADMIN_ROLE_KEY);
        addTenantMenu(menuIds);
    }
    private void addTenantMenu(List<Long> menuIds) {
        List<SysRoleBase> roles = sysRoleMapper.findByKeys(new String[]{RoleConstant.TENANT_ADMIN_ROLE_KEY}, null);
        if (CollectionUtil.isEmpty(roles)) {
            throw exception(SysInfraResultEnum.MENU_RELOAD_PERMISSION_EMPTY);
        }
        //为租户管理赋予权限
        roles.forEach(s->{
            sysRoleMenuService.insertList(menuIds,roleInfraConvert.toDto(s),s.getTenantId());
        });
    }


    @Override
    public List<SysMenuDto> query(SysMenuQueryContext context) {
        SysMenuBase menu = infraConvert.toSysMenuBase(context);
        List<SysMenuBase> menuList = sysMenuMapper.findListBy(menu);
        return infraConvert.toDtos(menuList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysMenuContext context) {
        SysMenuBase sysMenuBase = infraConvert.toSysMenuBase(context);
        SysMenuBase sourceMenu = sysMenuMapper.findById(sysMenuBase.getId());
        int row = sysMenuMapper.updateById(sysMenuBase);
        //只有当赋予租户改变的时候，才会重新授权给租户管理员
        if ( row <= 0) {
            throw exception(ResultStatusEnum.COMMON_UPDATE_ERROR);
        }
        if (sourceMenu.isGiveTenantMenu() && !sysMenuBase.isGiveTenantMenu()){
            //删除所有角色关联关系
            sysRoleMenuService.deleteByRolesAndMenuId(new String[]{RoleConstant.TENANT_ADMIN_ROLE_KEY}, context.getId());
        } else if(!sourceMenu.isGiveTenantMenu() && sysMenuBase.isGiveTenantMenu()){
            //删除所有角色关联关系（兼容处理，后续需要去掉）
            sysRoleMenuService.deleteByRolesAndMenuId(new String[]{RoleConstant.TENANT_ADMIN_ROLE_KEY}, context.getId());
            //更新角色菜单关联数据
            insertRoleMenu(context, context.getId());
        }
    }

    @Override
    public void delete(Long id) {
        boolean count = this.hasChild(id);
        if (count) {
            throw exception(SysInfraResultEnum.MENU_EXIST_SUB_MENU);
        }
        boolean exist = sysRoleMenuService.checkMenuExistRole(id);
        if (exist) {
            throw  exception(SysInfraResultEnum.MENU_ASSIGNED);
        }
        int delete = sysMenuMapper.deleteById(id);
    }
    private boolean hasChild(Long menuId) {
        SysMenuBase record = SysMenuBase.builder()
                .parentId(menuId)
                .build();
        return sysMenuMapper.findListByCount(record) > 0 ? true : false;
    }


    @Override
    public SysMenuDto findById(Long id) {
        SysMenuBase sysMenu = sysMenuMapper.findById(id);
        return infraConvert.toDto(sysMenu);
    }

    /**
     * @param context
     * @param menuId
     */
    private void insertRoleMenu(SysMenuContext context, Long menuId) {
        try {
            if (context.isGiveTenantMenu()){
                addTenantMenu(Arrays.asList(menuId));
            }
        } catch (Exception e) {
        }
    }
    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    private String getRouterPath(SysMenuDto menu) {
        String routerPath = menu.getPath();
        // 内链打开外网方式()
//        if (!MenuConstant.ROOT_ID.equals(menu.getParentId()) && isInnerLink(menu)) {
//            routerPath = innerLinkReplaceEach(routerPath);
//        }
        // 非外链并且是一级目录（类型为目录）
        if ((menu.isDir() && menu.isTopMenu()) && !menu.isFrameUrl()) {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
//        else if ( !menu.isFrame() && menu.isMenu()) {
//            routerPath = "/";
//        }
        return routerPath;
    }

    /**
     * 内链域名特殊字符替换
     *
     * @return 替换后的内链域名
     */
    private String innerLinkReplaceEach(String path) {
        return StringUtils.replaceEach(path, new String[]{Constants.HTTP, Constants.HTTPS, Constants.WWW, "."},
                new String[]{"", "", "", "/"});
    }

//    /**
//     * 是否为内链组件
//     * @param menu 菜单信息
//     * @return 结果
//     */
//    private boolean isInnerLink(SysMenuDto menu) {
//        return MenuConstant.FRAME_NO.equals(menu.getFrame()) && StringUtils.ishttp(menu.getPath());
//    }

//    /**
//     * 获取路由名称
//     *
//     * @param menu 菜单信息
//     * @return 路由名称
//     */
//    private String getRouteName(SysMenuDto menu) {
//        String routerName = StringUtils.capitalize(menu.getPath());
//        // 非外链并且是一级目录（类型为目录）
//        if (isMenuFrame(menu)) {
//            routerName = StringUtils.EMPTY;
//        }
//        return routerName;
//    }


    /**
     * 构建菜单树
     * @param list     需要处理的菜单
     * @return String
     */
    private List<SysMenuDto> buildMenuTree(List<SysMenuDto> list) {
        List<SysMenuDto> rst = new ArrayList<>();
        //查找所有的模块
        List<SysMenuDto> apps = list.stream().filter(SysMenuBase::isApp).collect(Collectors.toList());
        getSysMenuDtos(list, apps, rst);
        if (!rst.isEmpty()) return rst;
        // 做了兼容性处理
        apps = list.stream().filter(s-> s.isDir() && s.isTopMenu()).collect(Collectors.toList());
        getSysMenuDtos(list, apps, rst);
        if (rst.isEmpty()){
            return list;
        }
        return rst;
    }

    private void getSysMenuDtos(List<SysMenuDto> list, List<SysMenuDto> apps, List<SysMenuDto> rst) {
        if (CollectionUtil.isNotEmpty(apps)){
            apps.forEach(s->{
                List<SysMenuDto> childDtos =  getChild(list, s.getId());
                s.setChildren(childDtos);
                for (SysMenuDto parent:childDtos){
                    collectionChild(list,parent);
                }
                rst.add(s);
            });
        }
    }

    /**
     * 递归列表
     *
     * @param list 分类表
     * @param parent  父节点
     */
    private void collectionChild(List<SysMenuDto> list, SysMenuDto parent) {
        // 获取对应节点的子节点
        List<SysMenuDto> childList = getChild(list, parent.getId());
        parent.setChildren(childList);
        //递归收集
        for (SysMenuDto child : childList) {
            if (hasChild(list, child.getId())) {
                collectionChild(list, child);
            }
        }
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuDto> list, Long parentId) {
        SysMenuDto menuDto = list.stream().filter(s -> s.getParentId().equals(parentId)).findAny().orElse(null);
        return  Objects.nonNull(menuDto);
    }

    /**
     * 得到获取指定节点的子节点
     */
    private List<SysMenuDto> getChild(List<SysMenuDto> list, Long parentId) {
        return list.stream().filter(s-> s.getParentId().equals(parentId)).collect(Collectors.toList());
    }


//    /**
//     * 获取组件信息
//     * @param menu 菜单信息
//     * @return 组件信息
//     */
//    private String getComponent(SysMenuDto menu) {
//        String component = MenuConstant.LAYOUT;
//        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMenuFrame(menu)) {
//            component = menu.getComponent();
//        } else if (StringUtils.isEmpty(menu.getComponent()) && !MenuConstant.ROOT_ID.equals(menu.getParentId()) && isInnerLink(menu)) {
//            component = MenuConstant.INNER_LINK;
//        } else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu)) {
//            component = MenuConstant.PARENT_VIEW;
//        }
//        return component;
//    }

//    /**
//     * 是否为parent_view组件
//     * @param menu 菜单信息
//     * @return 结果
//     */
//    private boolean isParentView(SysMenuDto menu) {
//        return  MenuConstant.ROOT_ID.equals(menu.getParentId()) && MenuConstant.TYPE_DIR.equals(menu.getType());
//    }

}
