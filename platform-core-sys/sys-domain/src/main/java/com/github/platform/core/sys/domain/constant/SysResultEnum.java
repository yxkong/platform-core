package com.github.platform.core.sys.domain.constant;

import com.github.platform.core.standard.exception.BaseResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 适配基础设施层枚举
 * @author: yxkong
 * @date: 2023/12/22 14:53
 * @version: 1.0
 */
@Getter
@AllArgsConstructor
public enum SysResultEnum implements BaseResult {

    VERIFY_ERROR("2301", "验证码错误"),
    VERIFY_CREATE_FAIL("2302", "验证码生成失败！"),
    VERIFY_CODE_EXPIRE("2303", "验证码生成失败，请稍后再试！"),
    VERIFY_NOT_FOUND("2304","未找到对应的验证码！"),
    VERIFY_CODE_MINUTE("2305", "验证码1分钟只能获取一次！"),
    VERIFY_CODE_HOUR("2306", "验证码获取次数超过上限！"),

    REGISTERED("2307", "该账户已注册！"),
    MOBILE_REGISTERED("2308", "该手机号已注册！"),
    PWD_WARN("2309", "密码不符合(数字+字母+特殊字符)要求！"),
    NOT_FOUND_USER("2310", "未找到对应的用户！"),
    PWD_NOT_EQUALS("2311", "密码不正确！"),
    USER_DISABLED("2312", "该账户已禁用！"),
    NOT_FOUND_LOGIN_WAY("2313", "未找到对应登录方式的实现！"),
    LDAP_LOGIN_FAIL("2314", "未找到对应的账户，或密码错误，请重新尝试！"),
    SECRET_KEY_NOT_UNIQ("2315", "密钥不唯一，请重新生成！"),
    EXIST_USER("2316", "已存在对应的用户，请核查！"),

    MENU_RELOAD_PERMISSION_FAIL("2320", "重新加载菜单权限失败！"),
    MENU_EXIST_SUB_MENU("2321", "存在子菜单,不允许删除！"),
    MENU_ASSIGNED("2322", "菜单已分配,不允许删除！"),
    CONFIG_ADD_EXIST("2323","数据库中已经存在对应的配置key，请核查" ),
    CONFIG_NOT_UPDATE_KEY("2324","更新配置时，不允许修改配置key" ),
    MENU_RELOAD_PERMISSION_EMPTY("2325", "重新加载租户管理员菜单权限数据为空！"),
    MENU_PATH_IS_EXIST("2326", "路由地址已存在，请更换路由地址！"),

//    ROLE_ALREADY_EXIST("2330","该角色名或角色标识在该租户中已存在,请核验"),
//    FORBID_DELETE_ROLE("2331","该角色下存在用户，无法删除，请核验"),
    ROLE_NOT_FIND("2332","对应租户中未找到对应的角色"),

    DEPT_ADD_FAIL("2340", "新增部门失败，非管理员不能创建顶级部门！"),
    DEPT_EXIST("2341","部门名称已存在" ),
    DEPT_DISABLE("2342","父部门停用，不允许新增" ),
    DEPT_DELETE_EXIST_SUB_DEPT("2343", "存在下级部门,不允许删除"),
    DEPT_DELETE_EXIST_USER("2344", "部门存在用户,不允许删除"),

    DICT_EXIST("2345", "键值或标签已经存在，请勿重复提交"),
    DICT_TYPE_NOT_FOUND("2346", "对应的字典类型不存在"),
    DICT_TYPE_HAS_DATA("2347", "字典类型中有内容，禁止删除！"),
    DICT_EXIST_NAME("2348", "已存在相同的字典名称，请核对！"),
    DICT_EXIST_KEY("2349", "已存在相同的字典类型,请核对！"),

    ;



    private String status;
    private String message;
}
