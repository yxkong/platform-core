package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.persistence.mapper.sys.SysThirdUserMapper;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.constant.UserChannelEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.sys.domain.common.entity.SysThirdUserBase;
import com.github.platform.core.sys.domain.context.SysThirdUserContext;
import com.github.platform.core.sys.domain.context.SysThirdUserQueryContext;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.sys.domain.gateway.IThirdUserGateway;
import com.github.platform.core.sys.domain.model.user.ThirdUserEntity;
import com.github.platform.core.sys.infra.convert.SysThirdUserInfraConvert;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 三方用户网关层实现
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @time 2023-05-31 16:04:49.640
* @version 1.0
*/
@Service
public class ThirdUserGatewayImpl implements IThirdUserGateway {


    @Resource
    private SysThirdUserInfraConvert convert;
    @Resource
    private SysThirdUserMapper thirdUserMapper;


    @Override
    public PageBean<SysThirdUserDto> query(SysThirdUserQueryContext context) {
        SysThirdUserBase sysThirdUser = convert.toSysThirdUserBase(context);
        PageHelper.startPage(context.getPageNum(),context.getPageSize());
        List<SysThirdUserBase> list = thirdUserMapper.findListBy(sysThirdUser);
        return convert.ofPageBean(new PageInfo<>(list));
    }
    @Override
    public SysThirdUserDto insert(ThirdUserEntity thirdUserEntity, Long userId) {
        SysThirdUserBase thirdUser = convert.toSysThirdUserBase(thirdUserEntity,userId);
        thirdUser.setCreateTime(LocalDateTimeUtil.dateTime());
        thirdUser.setStatus(StatusEnum.OFF.getStatus());
        thirdUser.setChannel(thirdUserEntity.getChannel().getType());
        thirdUserMapper.insert(thirdUser);
        return convert.toDto(thirdUser);
    }
    @Override
    public void update(SysThirdUserContext context) {
        thirdUserMapper.updateById(context);
    }

    @Override
    public void delete(Long id) {
        thirdUserMapper.deleteById(id);
    }

    @Override
    public SysThirdUserDto findByChannel(String openId, UserChannelEnum channel,Integer tenantId) {
        SysThirdUserBase thirdUserDO = thirdUserMapper.findByChannel(openId, channel.getType(),tenantId);
        return convert.toDto(thirdUserDO);
    }

    @Override
    public SysThirdUserDto findById(Long id) {
        SysThirdUserBase base = thirdUserMapper.findById(id);
        return convert.toDto(base);
    }

    @Override
    public List<SysThirdUserDto> queryUsersByMobile(UserChannelEnum channel, List<String> mobiles,Integer tenantId) {
        List<SysThirdUserBase> list = thirdUserMapper.queryUsersByMobile(channel.getType(), mobiles,tenantId);
        return convert.toDtos(list);
    }
}
