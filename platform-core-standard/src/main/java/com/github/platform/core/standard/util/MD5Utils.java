package com.github.platform.core.standard.util;

import org.apache.commons.codec.digest.DigestUtils;
public class MD5Utils {

	/**
	 * md5的基础方法
	 * @param str
	 * @return
	 */
	public static String md5(String str){
		return DigestUtils.md5Hex(str);
	}

	/**
	 * 加盐md5加密
	 * @param str
	 * @param salt
	 * @return
	 */
	public static String md5Salt(String str,String salt){
		return md5(str+salt);
	}
}
