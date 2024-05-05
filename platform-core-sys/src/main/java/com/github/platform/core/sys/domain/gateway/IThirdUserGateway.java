package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import com.github.platform.core.sys.domain.context.SysThirdUserContext;
import com.github.platform.core.sys.domain.context.SysThirdUserQueryContext;
import com.github.platform.core.sys.domain.context.ThirdApproveContext;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.sys.domain.model.user.ThirdUserEntity;

import java.util.List;

/**
* 三方用户网关层，隔离模型和实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-05-31 16:04:49.640
* @version 1.0
*/
public interface IThirdUserGateway {
    /**
    * 查询三方用户列表
    * @param context
    * @return
    */
    PageBean<SysThirdUserDto> query(SysThirdUserQueryContext context);
    /**
    * 新增三方用户
    * @param thirdUserEntity
    * @return
    */
    SysThirdUserDto insert(ThirdUserEntity thirdUserEntity,Long userId);
    /**
    * 修改三方用户
    * @param context
    * @return
    */
    void update(SysThirdUserContext context);
    /**
    * 根据id删除三方用户记录
    * @param id
    * @return
    */
    void delete(Long id);

    /**
     * 根据用户id和渠道标识，查询用户
     * @param openId
     * @param channel
     * @return
     */

    SysThirdUserDto findByChannel(String openId, UserChannelEnum channel);

    /**
     * 根据主键查询信息
     * @param id
     * @return
     */
    SysThirdUserDto findById(Long id);

    /**
     * 审批
     * @param context
     */

    void approve(ThirdApproveContext context);

    /**
     * 根据渠道号+ 手机号查询用户
     * @param channel
     * @param mobiles
     * @return
     */
    List<SysThirdUserDto> queryUsersByMobile(UserChannelEnum channel,List<String> mobiles);
}
