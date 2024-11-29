package com.github.platform.core.sys.domain.gateway;

import com.github.platform.core.standard.entity.vue.OptionsDto;
import com.github.platform.core.sys.domain.constant.UserChannelEnum;
import com.github.platform.core.sys.domain.dto.SysConfigDto;
import com.github.platform.core.sys.domain.dto.SysDictDto;
import com.github.platform.core.sys.domain.dto.SysThirdUserDto;
import com.github.platform.core.sys.domain.dto.SysUserConfigDto;

import java.util.List;
import java.util.Map;

/**
 * sys 模块对外提供功能功能
 * @author: yxkong
 * @date: 2024/2/27 10:29
 * @version: 1.0
 */
public interface ISysCommonGateway {
    /**
     * 根据登录账户获取用户名
     * @param loginName 登录账户
     * @param  tenantId 租户id
     * @return
     */
    String getUserName(String loginName,Integer tenantId);

    /**
     * 根据字典类型，获取对应的kv
     * @param dictType
     * @return
     */
    Map<String,String> getDictMap(String dictType,Integer tenantId);

    /**
     * 根据字典类型获取字典
     * @param dictType
     * @return
     */
    List<SysDictDto> getDict(String dictType,Integer tenantId);

    /**
     * 根据字典名称
     * @param dictType
     * @return
     */

    List<OptionsDto> getOptionsByType(String dictType,Integer tenantId);

    /**
     * 根据配置key获取配置
     * @param configKey
     * @return
     */
    SysConfigDto getSysConfigByKey(Integer tenantId,String configKey);

    /**
     * 根据用户名获取三方用户信息
     * @param users
     * @return
     */
    List<SysThirdUserDto> queryChannelUsers(List<String> users, UserChannelEnum channelEnum);

    /**
     * 保存或更新用户配置
     * @param loginName
     * @param configKey
     * @param value
     */
    void saveOrUpdateConfig(String loginName,String configKey,String value,String valType);

    /**
     * 获取用户指定配置
     * @param loginName
     */
    SysUserConfigDto getConfig(String loginName,String configKey);

    /**
     * 获取配置值
     * @param loginName
     * @param configKey
     * @return
     */
    String getConfigVal(String loginName,String configKey);



    /**
     * 获取用户所有配置
     * @param loginName
     * @return
     */
    List<SysUserConfigDto> getUserAllConfig(String loginName);

}
