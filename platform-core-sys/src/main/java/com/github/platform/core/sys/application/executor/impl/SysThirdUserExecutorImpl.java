package com.github.platform.core.sys.application.executor.impl;

import com.github.platform.core.common.service.BaseExecutor;
import com.github.platform.core.sys.application.executor.ISysThirdUserExecutor;
import com.github.platform.core.sys.domain.context.SysThirdUserContext;
import com.github.platform.core.sys.domain.context.SysThirdUserQueryContext;
import com.github.platform.core.sys.domain.context.ThirdApproveContext;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.sys.domain.gateway.ThirdUserGateway;
import com.github.platform.core.standard.entity.dto.PageBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/*

* 三方用户执行器
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-05-31 16:04:49.640
* @version 1.0
*/
@Service
@Slf4j
public class SysThirdUserExecutorImpl extends BaseExecutor implements ISysThirdUserExecutor {
    @Resource
    private ThirdUserGateway gateway;
    /**
    * 查询三方用户列表
    * @param context
    * @return
    */
    @Override
    public PageBean<SysThirdUserDto> query(SysThirdUserQueryContext context){
        return gateway.query(context);
    };
    /**
    * 修改三方用户
    * @param context
    * @return
    */
    @Override
    public void update(SysThirdUserContext context) {
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
    public void approve(ThirdApproveContext context) {
        gateway.approve(context);
    }

    @Override
    public SysThirdUserDto detail(Long id) {
        return gateway.findById(id);
    }
}
