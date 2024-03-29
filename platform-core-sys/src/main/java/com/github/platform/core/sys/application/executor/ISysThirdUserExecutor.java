package com.github.platform.core.sys.application.executor;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.context.SysThirdUserContext;
import com.github.platform.core.sys.domain.context.SysThirdUserQueryContext;
import com.github.platform.core.sys.domain.context.ThirdApproveContext;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.sys.domain.dto.resp.UserResult;

/**
 * 三方用户执行器
 * @author: yxkong
 * @date: 2023/12/27 11:28
 * @version: 1.0
 */
public interface ISysThirdUserExecutor {
    /**
     * 查询三方用户列表
     *
     * @param context
     * @return
     */
    PageBean<SysThirdUserDto> query(SysThirdUserQueryContext context);

    /**
     * 修改三方用户
     *
     * @param context
     * @return
     */
    void update(SysThirdUserContext context);

    /**
     * 根据id删除三方用户记录
     *
     * @param id
     * @return
     */
    void delete(Long id);

    /**
     * 审批用户
     *
     * @param context
     * @return
     */
    void approve(ThirdApproveContext context);

    /**
     * 查询明细
     * @param id
     * @return
     */
    SysThirdUserDto detail(Long id);
}
