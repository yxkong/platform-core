package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.constant.LoginWayEnum;
import com.github.platform.core.sys.domain.context.ModifyPwdContext;
import com.github.platform.core.sys.domain.context.RegisterContext;
import com.github.platform.core.sys.domain.context.ResetPwdContext;
import com.github.platform.core.sys.domain.context.SysUserQueryContext;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import com.github.platform.core.sys.domain.dto.resp.PwdResult;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;

import java.util.List;
import java.util.Set;

/**
 * 用户网关
 *
 * @author: yxkong
 * @date: 2023/1/4 3:04 PM
 * @version: 1.0
 */
public interface ISysUserGateway {
    /**
     * 列表查询
     * @param context
     * @return
     */
    PageBean<SysUserDto> query(SysUserQueryContext context);

    /**
     * 查询指定部门下所有的用户，包含子部门
     * @param context
     * @return
     */
    PageBean<SysUserDto> queryByDept(SysUserQueryContext context);
    /**
     * 用户修改密码
     *
     * @param context
     * @return
     */
    void modifyPwd(ModifyPwdContext context);

    /**
     * 根据登录名查询用户
     *
     * @param loginName
     * @return
     */
    @Cacheable(cacheNames = CacheConstant.c30m, key = "'sys:u:l:' + #loginName", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    UserEntity findByLoginName(String loginName);

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    @Cacheable(cacheNames = CacheConstant.c30m, key = "'sys:u:m:' + #mobile", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    UserEntity findByMobile(String mobile);

    /**
     * 根据手机号查询数量
     * @param mobile
     * @return
     */
    Long findCountByMobile(String mobile);
    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    UserEntity findById(Long id);

    /**
     * 获取用户信息
     * @param id
     * @return
     */
    SysUserDto findByUserId(Long id);
    /**
     * 生成token,并将token放入缓存
     *
     * @param entity
     * @param roleKeys 指定角色
     * @param loginWay
     * @return
     */
    String generatorToken(UserEntity entity, Set<String> roleKeys, LoginWayEnum loginWay);

    /**
     * 刷新token
     * @param token
     * @param loginUserInfo
     * @return
     */
    void reloadToken(String  token, LoginUserInfo loginUserInfo);

    /**
     * 基础校验 密码+账户状态
     * @param loginName
     * @param pwd
     * @return
     */
    UserEntity baseAccountCheck(String loginName, String pwd);

    /**
     * 新增用户
     *
     * @param context
     * @return
     */
    UserEntity addUser(RegisterContext context);

    /**
     * 修改用户信息
     *
     * @param context
     * @return
     */
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CacheConstant.c30m,key = "'sys:u:l:'+#context.loginName",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CacheConstant.c30m,key = "'sys:u:m:'+#context.mobile",cacheManager = CacheConstant.cacheManager)
            }
    )
    void editUser(RegisterContext context);

    /**
     * 管理员重置密码
     *
     * @param context
     * @return
     */
    PwdResult resetPwd(ResetPwdContext context);


    /**
     * 退出登陆
     */
    void logout();


    /**
     * 根据租户和关键词模糊查询
     * @param key
     * @param tenantId
     * @return
     */
    List<SysUserDto> fuzzySearch(String key, Integer tenantId);

    /**
     * 查多个用户
     * @param users
     * @return
     */
    List<SysUserDto> queryByUsers(List<String> users);

    /**
     * 根据角色名称查询用户
     * @param roleKeys
     * @return
     */
    List<SysUserDto> findByRoleKeys(List<String> roleKeys,Integer tenantId);


    /**
     * 查询用户，不分页
     * @param query
     * @return
     */
    List<SysUserDto> findListBy(SysUserQueryContext query);
}
