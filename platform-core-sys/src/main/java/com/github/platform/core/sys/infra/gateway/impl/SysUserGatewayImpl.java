package com.github.platform.core.sys.infra.gateway.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.platform.core.auth.configuration.properties.AuthProperties;
import com.github.platform.core.auth.constant.DataScopeEnum;
import com.github.platform.core.auth.constant.RoleConstant;
import com.github.platform.core.auth.entity.LoginUserInfo;
import com.github.platform.core.auth.entity.TokenCacheEntity;
import com.github.platform.core.auth.gateway.ITokenCacheGateway;
import com.github.platform.core.auth.service.ITokenService;
import com.github.platform.core.auth.util.AuthUtil;
import com.github.platform.core.auth.util.LoginUserInfoUtil;
import com.github.platform.core.cache.domain.constant.CacheConstant;
import com.github.platform.core.common.gateway.BaseGatewayImpl;
import com.github.platform.core.common.utils.CollectionUtil;
import com.github.platform.core.common.utils.EncryptUtil;
import com.github.platform.core.common.utils.JsonUtils;
import com.github.platform.core.common.utils.StringUtils;
import com.github.platform.core.persistence.mapper.sys.SysUserLogMapper;
import com.github.platform.core.persistence.mapper.sys.SysUserMapper;
import com.github.platform.core.standard.constant.StatusEnum;
import com.github.platform.core.standard.entity.dto.PageBean;
import com.github.platform.core.standard.util.LocalDateTimeUtil;
import com.github.platform.core.standard.util.MD5Utils;
import com.github.platform.core.sys.domain.common.entity.SysUserBase;
import com.github.platform.core.sys.domain.common.entity.SysUserLogBase;
import com.github.platform.core.sys.domain.constant.ConfigKeyConstant;
import com.github.platform.core.sys.domain.constant.DeptConstant;
import com.github.platform.core.sys.domain.constant.LoginWayEnum;
import com.github.platform.core.sys.domain.context.ModifyPwdContext;
import com.github.platform.core.sys.domain.context.RegisterContext;
import com.github.platform.core.sys.domain.context.ResetPwdContext;
import com.github.platform.core.sys.domain.context.SysUserQueryContext;
import com.github.platform.core.sys.domain.dto.*;
import com.github.platform.core.sys.domain.dto.resp.PwdResult;
import com.github.platform.core.sys.domain.gateway.*;
import com.github.platform.core.sys.domain.model.user.UserEntity;
import com.github.platform.core.sys.infra.constant.SysInfraResultEnum;
import com.github.platform.core.sys.infra.convert.SysUserInfraConvert;
import com.github.platform.core.sys.infra.util.ResultPageBeanUtil;
import com.github.platform.core.sys.infra.util.UserAgentUtil;
import com.github.platform.core.web.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户网关
 *
 * @author: yxkong
 * @date: 2023/1/4 4:04 PM
 * @version: 1.0
 */
@Slf4j
@Service
public class SysUserGatewayImpl extends BaseGatewayImpl implements ISysUserGateway {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysUserLogMapper sysUserLogMapper;
    @Resource
    private SysUserInfraConvert userInfraConvert;
    @Resource(name = CacheConstant.sysTokenService)
    private ITokenService tokenService;
    @Resource
    private ITokenCacheGateway tokenCacheGateway;
    @Resource
    private ISysRoleGateway roleGateway;
    @Resource
    private ISysMenuGateway menuGateway;

    @Resource
    private ISysConfigGateway configGateway;
    @Resource
    private ISysDeptGateway deptGateway;
    @Resource
    private AuthProperties authProperties;
    @Resource
    private ISysUserRoleGateway sysUserRoleGateway;

    @Override
    public PageBean<SysUserDto> query(SysUserQueryContext context) {
        SysUserBase sysUser = userInfraConvert.toSysUserBase(context);
        //非管理员只能查自己租户内的数据，管理员可以查看所有的租户
        if (!AuthUtil.isSuperAdmin()) {
            sysUser.setTenantId(context.getTenantId());
        }
        PageHelper.startPage(context.getPageNum(), context.getPageSize());
        List<SysUserDto> list = null;
        if (Objects.nonNull(context.getRoleKey())){
            list = sysUserMapper.findListByRole(sysUser,context.getRoleKey());
        } else {
            list = sysUserMapper.findListBy(sysUser);
        }
        return ResultPageBeanUtil.pageBean(new PageInfo<>(list));
    }
    @Override
    public PageBean<SysUserDto> queryByDept(SysUserQueryContext context) {
        //获取所有的子部门
        List<SysDeptDto> deptDtos= deptGateway.findAllDept(context.getDeptId());
        Set<Long> deptIds = deptDtos.stream().map(SysDeptDto::getId).collect(Collectors.toSet());
        PageHelper.startPage(context.getPageNum(), context.getPageSize());
        SysUserBase sysUser = userInfraConvert.toSysUserBase(context);
        List<SysUserDto> list = sysUserMapper.findListByDept(deptIds, sysUser);
        return ResultPageBeanUtil.pageBean(new PageInfo<>(list));
    }
    private String getDefaultPwd(){
        SysConfigDto config = configGateway.getConfig(LoginUserInfoUtil.getTenantId(),ConfigKeyConstant.SYS_DEFAULT_PASSWORD);
        if (Objects.isNull(config) || StringUtils.isEmpty(config.getValue())){
            return "000000";
        }
        return config.getValue();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public UserEntity addUser(RegisterContext context) {
        Pair<String, PwdResult> pair = initPwd(context.getLoginName(),context.getPwd());
        SysUserBase sysUser = userInfraConvert.of(context, pair.getKey(), pair.getValue().getMd5Pwd());
        int insert = sysUserMapper.insert(sysUser);
        SysUserLogBase sysUserLog = userInfraConvert.of(context, sysUser.getCreateBy(), context.getLogBizTypeEnum());
        String ip = WebUtil.getIpAddress();
        sysUserLog.setRequestIp(ip);
        sysUserLog.setTenantId(sysUser.getTenantId());
        int log = sysUserLogMapper.insert(sysUserLog);
        //级联保存用户角色关系
        sysUserRoleGateway.addUserRole(sysUser.getId(),sysUser.getTenantId() ,context.getRoleKeys());
        return userInfraConvert.target(sysUser, null);
    }

    /**
     * 初始化用户密码
     *  1，优先使用传递进来的；
     *  2，没有设置，使用默认密码
     * @param loginName
     * @param userPwd
     * @return
     */
    private Pair<String,PwdResult> initPwd(String loginName,String userPwd){
        String pwd = null;
        if (StringUtils.isNotEmpty(userPwd)) {
            pwd = userPwd;
        } else {
            pwd = getDefaultPwd();
        }
        //加密密码
        Pair<String, String> pair = EncryptUtil.getInstance().md5(pwd);
        return Pair.of(pair.getKey(),new PwdResult(loginName,pwd,pair.getValue()));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON +'l:'+ #loginName+#tenantId",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON +'m:'+ #mobile+#tenantId",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON +'s:'+ #secretKey",cacheManager = CacheConstant.cacheManager)
            }
    )
    public void deleteCache(String loginName, String mobile,String secretKey, Integer tenantId) {

    }

    /**
     * 修改用户信息
     *
     * @param context
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON +'id:'+ #context.id",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON +'l:'+ #context.loginName+#context.tenantId",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON +'m:'+ #context.mobile+#context.tenantId",cacheManager = CacheConstant.cacheManager),
                    @CacheEvict(cacheNames = CACHE_NAME,key = "#root.target.PREFIX_COLON +'s:'+ #context.secretKey",cacheManager = CacheConstant.cacheManager)
            }
    )
    public void editUser(RegisterContext context) {
        SysUserBase sysUser = userInfraConvert.of(context, "", "");
        int rows = sysUserMapper.updateById(sysUser);
        sysUserRoleGateway.addUserRole(sysUser.getId(), sysUser.getTenantId(), context.getRoleKeys());
        //禁用以后，清除用户登录信息
        if (StatusEnum.OFF.getStatus().equals(context.getStatus())) {
            tokenService.expireByLoginName(context.getTenantId(),context.getLoginName());
        }
    }


    /**
     * 管理员重置密码
     *
     * @param context
     * @return
     */
    @Override
    public PwdResult resetPwd(ResetPwdContext context) {
        String salt = RandomStringUtils.randomAlphabetic(6);
        String defaultPwd = getDefaultPwd();
        Pair<String, String> pair = EncryptUtil.getInstance().md5(defaultPwd);
        SysUserBase sysUser = userInfraConvert.of(context, pair.getKey(), pair.getValue());
        sysUser.setLastModifyPwdTime(null);
        int rows = sysUserMapper.modifyPwd(sysUser);
        //清除
        tokenService.expireByLoginName(context.getTenantId(),context.getLoginName());
        return new PwdResult(context.getLoginName(), defaultPwd, pair.getValue());
    }

    /**
     * 退出登陆
     */
    @Override
    public void logout() {
        //清除上次token的登陆信息
        tokenService.expireByToken(LoginUserInfoUtil.getToken());
    }


    /**
     * 修改密码
     *
     * @param context
     * @return
     */
    @Override
    public void modifyPwd(ModifyPwdContext context) {
        String salt = RandomStringUtils.randomAlphabetic(6);
        String md5Pwd = MD5Utils.md5Salt(context.getNewPwd(), salt);
        SysUserBase sysUser = userInfraConvert.of(context, salt, md5Pwd);
        sysUser.setLastModifyPwdTime(LocalDateTimeUtil.dateTime());
        int rows = sysUserMapper.modifyPwd(sysUser);
        //清除上次token的登陆信息
        tokenService.expireByLoginName(LoginUserInfoUtil.getTenantId(),LoginUserInfoUtil.getLoginName());
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key =  "#root.target.PREFIX_COLON +'l:'+ #loginName+#tenantId", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public UserEntity findByLoginName(String loginName,Integer tenantId) {
        if (StringUtils.isEmpty(loginName)) {
            return null;
        }
        SysUserBase sysUser = sysUserMapper.findByLoginName(loginName,tenantId);

        return getUserEntity(sysUser);
    }

    private UserEntity getUserEntity(SysUserBase sysUser) {
        if (Objects.isNull(sysUser)) {
            return null;
        }
        SysDeptDto deptDto = deptGateway.findById(sysUser.getDeptId());
        if (Objects.isNull(deptDto)){
            return null;
        }
        return userInfraConvert.target(sysUser, deptDto.getDeptName());
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key =  "#root.target.PREFIX_COLON +'m:'+ #mobile+#tenantId", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public UserEntity findByMobile(String mobile,Integer tenantId) {
        if (StringUtils.isEmpty(mobile)){
            return null;
        }
        SysUserBase sysUser = sysUserMapper.findByMobile(mobile,tenantId);
        return getUserEntity(sysUser);
    }
    @Override
    @Cacheable(cacheNames = CACHE_NAME, key =  "#root.target.PREFIX_COLON +'s:'+ #secretKey", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public UserEntity findBySecretKey(String secretKey) {
        if (StringUtils.isEmpty(secretKey)){
            return null;
        }
        SysUserBase sysUser = sysUserMapper.findBySecretKey(secretKey);
        return getUserEntity(sysUser);
    }

    @Override
    public UserEntity findById(Long id) {
        SysUserBase sysUser = sysUserMapper.findById(id);
        return getUserEntity(sysUser);
    }

    @Override
    @Cacheable(cacheNames = CACHE_NAME, key =  "#root.target.PREFIX_COLON +'id:'+ #id", cacheManager = CacheConstant.cacheManager, unless = "#result == null")
    public SysUserDto findByUserId(Long id) {
        SysUserBase record = sysUserMapper.findById(id);
        return userInfraConvert.toDto(record);
    }

    @Override
    public LoginUserInfo generatorToken(UserEntity entity, Set<String> roleKeys, LoginWayEnum loginWay) {
        //演示环境下不过期历史登录，非演示环境，新的登录会过期历史登录
        if (!authProperties.getSys().isDemoMode()){
            log.info("非演示环境，踢出用户{}之前登录",entity.getLoginName());
            tokenService.expireByLoginName(entity.getTenantId(),entity.getLoginName());
        }
        String token = StringUtils.uuidRmLine();
        LoginUserInfo loginUserInfo = getLoginUserInfo(entity, roleKeys, loginWay, token);
        LoginUserInfoUtil.setLoginUserInfo(loginUserInfo);
        //缓存token的登陆信息
        tokenService.saveOrUpdate(entity.getTenantId(), token,entity.getLoginName(),entity.getLoginName(), JsonUtils.toJson(loginUserInfo),true);
        return loginUserInfo;
    }
    private LoginUserInfo getLoginUserInfo(UserEntity entity, Set<String> roleKeys, LoginWayEnum loginWay, String token) {
        LoginUserInfo loginUserInfo = userInfraConvert.target(entity);
        loginUserInfo.setToken(token);
        loginUserInfo.setEmail(entity.getEmail());
        loginUserInfo.setSecretKey(entity.getSecretKey());
        loginUserInfo.setLoginWay(loginWay.getType());
        loginUserInfo.setLoginTime(LocalDateTimeUtil.dateTimeDefault());
        loginUserInfo.setRegisterTime(LocalDateTimeUtil.dateTimeDefault(entity.getRegisterTime()));

        permsDeal(entity, roleKeys, loginUserInfo);
        Pair<String, String> pair = UserAgentUtil.getOsAndBrowser();
        loginUserInfo.setBrowser(pair.getValue());
        loginUserInfo.setOs(pair.getKey());
        loginUserInfo.setRequestIp(WebUtil.getIpAddress());
        loginUserInfo.setStatus(StatusEnum.ON.getVal());
        return loginUserInfo;
    }

    @Override
    public void reloadToken(String loginName, Integer tenantId) {
        UserEntity userEntity = findByLoginName(loginName,tenantId);
        TokenCacheEntity tokenCacheEntity = tokenCacheGateway.findByLoginName(tenantId, loginName);
        if (Objects.isNull(tokenCacheEntity)){
            return;
        }
        LoginUserInfo userInfo = JsonUtils.fromJson(tokenCacheEntity.getLoginInfo(), LoginUserInfo.class);
        LoginUserInfo loginUserInfo = getLoginUserInfo(userEntity, null, LoginWayEnum.of(userInfo.getLoginWay()), userInfo.getToken());
        tokenService.saveOrUpdate(tenantId, tokenCacheEntity.getToken(),loginName,LoginUserInfoUtil.getLoginName(), JsonUtils.toJson(loginUserInfo),false);
    }

    @Override
    public void reloadToken(String token,LoginUserInfo loginUserInfo) {
        //只有本人刷新自己的信息的时候，线程变量才会更新
        if (Objects.equals(loginUserInfo.getLoginName(), LoginUserInfoUtil.getLoginName())){
            LoginUserInfoUtil.setLoginUserInfo(loginUserInfo);
        }
        tokenService.saveOrUpdate(loginUserInfo.getTenantId(),token,loginUserInfo.getLoginName(),LoginUserInfoUtil.getLoginName(), JsonUtils.toJson(loginUserInfo),false);
    }

    /**
     * 处理权限
     * @param entity
     * @param roleKeys
     * @param loginUserInfo
     */
    private void permsDeal(UserEntity entity, Set<String> roleKeys, LoginUserInfo loginUserInfo) {
        //角色处理
        List<SysRoleDto> roleEntities = null;
        if (CollectionUtil.isEmpty(roleKeys)){
            //获取用户的角色
            roleEntities = roleGateway.findRoleByUserId(entity.getId());
        } else {
            //使用传递过来的角色
            List<String> roles = new ArrayList<>(roleKeys);
            roleEntities = roleGateway.findByKeys(roles,entity.getTenantId());
        }
        if (CollectionUtil.isNotEmpty(roleEntities)){
            loginUserInfo.setRoleKeys(roleEntities.stream().map(SysRoleDto::getKey).filter(Objects::nonNull).collect(Collectors.toList()));
            loginUserInfo.setRoleNames(roleEntities.stream().map(SysRoleDto::getName).filter(Objects::nonNull).collect(Collectors.toList()));
            loginUserInfo.setRoleIds(roleEntities.stream().map(SysRoleDto::getId).collect(Collectors.toList()));
        }
        if (loginUserInfo.isSuperAdmin()){
            Set<DataScopeEnum> dataScopes = new HashSet<>();
            dataScopes.add(DataScopeEnum.ALL);
            loginUserInfo.setDataScopes(dataScopes);
            loginUserInfo.setPerms(Stream.of(RoleConstant.ALL_PERMS).collect(Collectors.toSet()));
        } else if(loginUserInfo.isTenantAdmin()){
            Set<DataScopeEnum> dataScopes = new HashSet<>();
            dataScopes.add(DataScopeEnum.ALL);
            loginUserInfo.setDataScopes(dataScopes);
            List<SysMenuDto> menuDtos = menuGateway.findByTenantId(entity.getTenantId());
            loginUserInfo.setPerms(getPerms(menuDtos));
        }else {
            //数据权限
            if (CollectionUtil.isNotEmpty(roleEntities)){
                loginUserInfo.setDataScopes(roleEntities.stream().map(SysRoleDto::getDataScope).map(DataScopeEnum::of).filter(Objects::nonNull).collect(Collectors.toSet()));
            }
            //非默认部门的时候，查询所在的所有部门
            if (entity.getDeptId()!= null && !DeptConstant.DEFAULT_ID.equals(entity.getDeptId())){
                //所有部门
                Set<Long> deptIds = deptGateway.findAllDept(entity.getDeptId()).stream().map(SysDeptDto::getId).collect(Collectors.toSet());
                loginUserInfo.setDeptIds(deptIds);
            }
            //非管理员查询菜单权限
            List<SysMenuDto> menuDtos = menuGateway.findMenuByRoleIds(loginUserInfo.getRoleIds());
            loginUserInfo.setPerms(getPerms(menuDtos));
        }
    }

    private static Set<String> getPerms(List<SysMenuDto> menuDtos) {
        if (CollectionUtil.isEmpty(menuDtos)){
            return new HashSet<>();
        }
        return menuDtos.stream()
                .filter(menuDto -> StringUtils.isNotBlank(menuDto.getPerms()))
                .map(SysMenuDto::getPerms)
                .flatMap(perms -> Stream.of(perms.split(",")))
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toSet());
    }

    @Override
    public UserEntity baseAccountCheck(String loginName, Integer tenantId, String pwd) {
        UserEntity userEntity = this.findByLoginName(loginName,tenantId);
        if (Objects.isNull(userEntity)) {
            throw exception(SysInfraResultEnum.NOT_FOUND_USER);
        }
        if (!userEntity.login(pwd)) {
            throw exception(SysInfraResultEnum.PWD_NOT_EQUALS);
        }
        if (userEntity.disable()) {
            throw exception(SysInfraResultEnum.USER_DISABLED);
        }
        return userEntity;
    }

    @Override
    public List<SysUserDto> fuzzySearch(String key, Integer teantId) {
        return sysUserMapper.fuzzySearch(key,teantId);
    }

    @Override
    public List<SysUserDto> queryByUsers(List<String> users) {
        if (CollectionUtil.isEmpty(users)){
            return new ArrayList<>();
        }
        // 去重用户
        List<String> distinctUsers = users.stream().distinct().collect(Collectors.toList());
        return sysUserMapper.queryByUsers(distinctUsers);
    }

    @Override
    public List<SysUserDto> findByRoleKeys(List<String> roleKeys,Integer tenantId) {
        return sysUserMapper.queryByRoleKeys(roleKeys,tenantId);
    }

    @Override
    public List<SysUserDto> findListBy(SysUserQueryContext context) {
        SysUserBase sysUser = userInfraConvert.toSysUserBase(context);
        return sysUserMapper.findListBy(sysUser);
    }
}
