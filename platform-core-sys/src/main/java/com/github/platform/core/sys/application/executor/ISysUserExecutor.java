package com.github.platform.core.sys.application.executor;

import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.context.RegisterContext;
import com.github.platform.core.sys.domain.context.ResetPwdContext;
import com.github.platform.core.sys.domain.context.SysUserQueryContext;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import com.github.platform.core.sys.domain.dto.resp.PwdResult;
import com.github.platform.core.sys.domain.model.user.UserEntity;

import java.util.List;

/**
 *  用户账号管理执行器
 * @author: yxkong
 * @date: 2023/12/27 11:05
 * @version: 1.0
 */
public interface ISysUserExecutor {

    /**
     * 查询
     * @param context
     * @return
     */
    PageBean<SysUserDto> query(SysUserQueryContext context);

    /**
     * 查询
     * @param context
     * @return
     */
    PageBean<SysUserDto> queryByDept(SysUserQueryContext context);

    /**
     * 新增用户
     * @param context
     * @return
     */
    UserEntity insert(RegisterContext context);

    /**
     * 更新用户
     * @param context
     */
    void update(RegisterContext context);

    /**
     * 重置密码
     * @param context
     * @return
     */
    PwdResult resetPwd(ResetPwdContext context);

    /**
     * 刷新token
     * @param token
     * @param loginUserInfo
     * @return
     */
    void reloadToken(String  token, LoginUserInfo loginUserInfo);

    /**
     * 查询用户详情
     * @param id
     * @return
     */
    SysUserDto detail(Long id);

    /**
     * 用户模糊搜索
     * @param key
     * @return
     */
    List<OptionsDto> fuzzySearch(String key);

    /**
     * 查询下来用户
     * @param query
     * @return
     */
    List<OptionsDto> queryUsers(SysUserQueryContext query);
}
