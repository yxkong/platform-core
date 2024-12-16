package com.github.platform.core.sys.adapter.api.convert;

import com.github.platform.core.sys.adapter.api.command.account.*;
import com.github.platform.core.sys.domain.constant.LoginWayEnum;
import com.github.platform.core.sys.domain.constant.VerifyTypeEnum;
import com.github.platform.core.sys.domain.context.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
/**
* 用户信息Controller到Application层的适配
* @website <a href="https://www.5ycode.com/">5ycode</a>
* @author yxkong
* @datetime 2023-08-15 10:55:06.651
* @version 1.0
*/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SysUserAdapterConvert {
    /**
    * 查询实体转查询上下文
    * @param query 查询实体
    * @return 查询上下文
    */
    SysUserQueryContext toQuery(SysUserQuery query);
    /**
    * 操作实体转操作上下文
    * @param cmd 操作实体
    * @return 操作上下文
    */
    SysUserContext toContext(SysUserCmd cmd);
    default String toVerifyType(VerifyTypeEnum verifyType) {
        return verifyType.getType();
    }

    default VerifyTypeEnum toVerifyTypeEnum(String verifyType) {
        return VerifyTypeEnum.of(verifyType);
    }

    default String toLoginWay(LoginWayEnum loginWay) {
        return loginWay.getType();
    }

    default LoginWayEnum toLoginWayEnum(String loginWay) {
        return LoginWayEnum.of(loginWay);
    }

    LoginContext toLogin(NormalLoginCmd cmd);

    LoginContext toLogin(SmsLoginCmd cmd);


    ModifyPwdContext toModifyPwd(ModifyPwdCmd cmd);

    ResetPwdContext toRestPwd(ResetPwdCmd cmd);

    RegisterContext toRegister(SysUserCmd cmd);
    RegisterContext profileToRegister(UserProfileCmd cmd);
}