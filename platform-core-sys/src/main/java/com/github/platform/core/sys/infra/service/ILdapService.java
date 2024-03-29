package com.github.platform.core.sys.infra.service;

import com.github.platform.core.sys.domain.model.user.ThirdUserEntity;
import org.apache.commons.lang3.tuple.Pair;

import javax.naming.NamingException;

public interface ILdapService {

	/**
	 * 用户及密码是否在ldap系统存在
	 * @param username 用户名需要与ldap存储一致（目前ldap存储是汉字 eg：）
	 * @param password
	 * @return false：用户名或者密码错误、true：认证成功
	 */
	Pair<Boolean,ThirdUserEntity> validate(String username, String password);

	/**
	 * 是否存在
	 * @param username
	 * @return
	 */
	boolean isExist(String username) throws NamingException;


	/**
	 * 根据过滤条件搜索用户
	 * @param filter
	 * @return
	 * @throws NamingException
	 */
	ThirdUserEntity searchUserInfo(String filter) throws NamingException;
}
