package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.auth.application.executor.SysExecutor;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.application.executor.ISysThirdUserExecutor;
import com.github.platform.core.sys.domain.context.RegisterContext;
import com.github.platform.core.sys.domain.context.SysThirdUserContext;
import com.github.platform.core.sys.domain.context.SysThirdUserQueryContext;
import com.github.platform.core.sys.domain.context.ThirdApproveContext;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.sys.domain.dto.SysUserDto;
import com.github.platform.core.sys.domain.gateway.ISysUserGateway;
import com.github.platform.core.sys.domain.gateway.IThirdUserGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* 三方用户执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-05-31 16:04:49.640
* @version 1.0
*/
@Service
@Slf4j
public class SysThirdUserExecutorImpl extends SysExecutor implements ISysThirdUserExecutor {
    @Resource
    private IThirdUserGateway gateway;
    @Resource
    private ISysUserGateway sysUserGateway;
    /**
    * 查询三方用户列表
    * @param context
    * @return
    */
    @Override
    public PageBean<SysThirdUserDto> query(SysThirdUserQueryContext context){
        context.setTenantId(getTenantId(context));
        return gateway.query(context);
    };
    /**
    * 修改三方用户
    * @param context
    * @return
    */
    @Override
    public void update(SysThirdUserContext context) {
        context.setTenantId(getTenantId(context));
        gateway.update(context);
    }
    /**
    * 根据id删除三方用户记录
    * @param id
    * @return
    */
    @Override
    public void delete(Long id) {
        gateway.delete(id);
    }

    /**
     * 审批用户
     * @param context
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approve(ThirdApproveContext context) {
        //更新三方用户状态
        SysThirdUserContext thirdUser = SysThirdUserContext.builder().id(context.getId()).status(context.getStatus()).build();
        gateway.update(thirdUser);
        SysUserDto sysUserDto = sysUserGateway.findByUserId(context.getId());
        // 更新用户状态
        RegisterContext sysUser = RegisterContext.builder()
                .id(context.getUserId())
                .loginName(sysUserDto.getLoginName())
                .mobile(sysUserDto.getMobile())
                .deptId(context.getDeptId())
                .status(context.getStatus())
                .secretKey(sysUserDto.getSecretKey())
                .build();
        sysUserGateway.editUser(sysUser);
    }

    @Override
    public SysThirdUserDto detail(Long id) {
        return gateway.findById(id);
    }
}
