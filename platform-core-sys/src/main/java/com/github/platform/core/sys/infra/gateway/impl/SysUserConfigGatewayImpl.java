package com.github.platform.core.sys.infra.gateway.impl;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.sys.domain.common.entity.SysUserConfigBase;
import com.github.platform.core.sys.domain.context.SysUserConfigContext;
import com.github.platform.core.sys.domain.dto.SysUserConfigDto;
import com.github.platform.core.sys.domain.gateway.ISysUserConfigGateway;
import com.github.platform.core.sys.infra.convert.SysUserConfigInfraConvert;
import com.github.platform.core.persistence.mapper.sys.SysUserConfigMapper;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
/**
 * 用户配置网关层实现
 * @website <a href="https://www.5ycode.com/">5ycode</a>
 * @author yxkong
 * @date 2024-03-27 16:10:38.663
 * @version 1.0
 */
@Service
public class SysUserConfigGatewayImpl extends BaseGatewayImpl implements ISysUserConfigGateway {

    @Resource
    private SysUserConfigMapper sysUserConfigMapper;
    @Resource
    private SysUserConfigInfraConvert sysUserConfigConvert;

    @Override
    public SysUserConfigDto insert(SysUserConfigContext context) {
        SysUserConfigBase sysUserConfigBase = sysUserConfigConvert.toSysUserConfigBase(context);
        sysUserConfigMapper.insert(sysUserConfigBase);
        return sysUserConfigConvert.toDto(sysUserConfigBase);
    }

    @Override
    public SysUserConfigDto findById(Long id) {
        SysUserConfigBase record = sysUserConfigMapper.findById(id);
        return sysUserConfigConvert.toDto(record);
    }

    @Override
    public Boolean update(SysUserConfigContext context){
        SysUserConfigBase record = sysUserConfigConvert.toSysUserConfigBase(context);
        return sysUserConfigMapper.updateById(record) > 0;
    }

    @Override
    public List<SysUserConfigDto> findListBy(SysUserConfigContext context){
        SysUserConfigBase record = sysUserConfigConvert.toSysUserConfigBase(context);
        List<SysUserConfigBase> list = sysUserConfigMapper.findListBy(record);
        return sysUserConfigConvert.toDtos(list);
    }

    @Override
    public SysUserConfigDto getConfig(String loginName, String configKey) {
        List<SysUserConfigBase> list = sysUserConfigMapper.findListBy(SysUserConfigBase.builder().loginName(loginName).configKey(configKey).build());
        if (CollectionUtil.isEmpty(list)){
            return null;
        }
        return sysUserConfigConvert.toDto(list.get(0));
    }

    @Override
    public List<SysUserConfigDto> getUserAllConfig(String loginName) {
        List<SysUserConfigBase> list = sysUserConfigMapper.findListBy(SysUserConfigBase.builder().loginName(loginName).build());
        return sysUserConfigConvert.toDtos(list);
    }
}
