package com.github.platform.core.sys.infra.gateway.impl;

import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import com.github.platform.core.sys.domain.context.SysUserConfigContext;
import com.github.platform.core.sys.domain.dto.*;
import com.github.platform.core.sys.domain.gateway.*;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 公共实现
 * @author: yxkong
 * @date: 2024/2/27 10:31
 * @version: 1.0
 */
@Service
public class SysCommonGatewayImpl implements ISysCommonGateway {
    @Resource
    private ISysDictGateway sysDictGateway;
    @Resource
    private ISysConfigGateway sysConfigGateway;
    @Resource
    private ISysUserGateway sysUserGateway;
    @Resource
    private IThirdUserGateway thirdUserGateway;
    @Resource
    private ISysUserConfigGateway userConfigGateway;
    @Override
    public String getUserName(String loginName) {
        UserEntity userEntity = sysUserGateway.findByLoginName(loginName);
        if (Objects.isNull(userEntity)){
            return loginName;
        }
        return userEntity.getUserName();
    }

    @Override
    public Map<String, String> getDictMap(String dictType) {
        List<SysDictDto> dictDtos = sysDictGateway.findByType(dictType);
        return Optional.ofNullable(dictDtos)
                .map(list -> list.stream().collect(Collectors.toMap(SysDictDto::getKey, SysDictDto::getLabel)))
                .orElseGet(Collections::emptyMap);
    }

    @Override
    public List<SysDictDto> getDict(String dictType) {
        return sysDictGateway.findByType(dictType);
    }

    @Override
    public List<OptionsDto> getOptionsByType(String dictType) {
        List<OptionsDto> rst = new ArrayList<>();
        List<SysDictDto> dictDtos = sysDictGateway.findByType(dictType);
        if (CollectionUtil.isEmpty(dictDtos)){
            return rst;
        }
        dictDtos.forEach(s->{
            rst.add(new OptionsDto(s.getKey(),s.getLabel()));
        });
        return rst;
    }

    @Override
    public SysConfigDto getSysConfigByKey(Integer tenantId,String configKey) {
        return sysConfigGateway.getConfig(tenantId,configKey);
    }

    @Override
    public List<SysThirdUserDto> queryChannelUsers(List<String> users,UserChannelEnum channelEnum) {
        List<SysThirdUserDto> rst = new ArrayList<>();
        // 查到系统用户
        List<SysUserDto> userList = sysUserGateway.queryByUsers(users);
        if (CollectionUtil.isEmpty(userList)){
            return rst;
        }
        List<String> mobiles = userList.stream()
                .map(SysUserDto::getMobile)
                .distinct()
                .collect(Collectors.toList());
        return thirdUserGateway.queryUsersByMobile(channelEnum, mobiles);
    }

    @Override
    public void saveOrUpdateConfig(String loginName, String configKey, String value,String valType) {
        SysUserConfigDto dto = userConfigGateway.getConfig(loginName,configKey);
        SysUserConfigContext context = SysUserConfigContext.builder().loginName(loginName).configKey(configKey).value(value).valType(valType).build();
        if (Objects.isNull(dto)){
            userConfigGateway.insert(context);
        } else {
            context.setId(dto.getId());
            userConfigGateway.update(context);
        }
    }

    @Override
    public SysUserConfigDto getConfig(String loginName, String configKey) {
        return userConfigGateway.getConfig(loginName,configKey);
    }

    @Override
    public String getConfigVal(String loginName, String configKey) {
        SysUserConfigDto config = userConfigGateway.getConfig(loginName, configKey);
        if (Objects.isNull(config) || StringUtils.isEmpty(config.getValue())){
            return null;
        }
        return config.getValue();
    }

    @Override
    public List<SysUserConfigDto> getUserAllConfig(String loginName) {
        return userConfigGateway.getUserAllConfig(loginName);
    }
}
