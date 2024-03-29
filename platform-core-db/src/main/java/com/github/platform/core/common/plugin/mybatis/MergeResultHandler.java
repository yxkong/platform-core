package com.github.platform.core.common.plugin.mybatis;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 结果集处理
 * 
 * @author yxkong
 *
 */
public class MergeResultHandler {

	public MergeResultHandler() {

	}

	public static Object mergeResultSet(MappedStatement mappedStatement, BoundSql boundSql, Object resultObj,
			RowBounds rowBounds) throws ClassNotFoundException {

		String id = mappedStatement.getId();
		String className = id.substring(0, id.lastIndexOf("."));

		String methodName = id.substring(id.lastIndexOf(".") + 1);
		Class<?> classObj = Class.forName(className);
		Method[] methods = classObj.getMethods();
		Object returnClass = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				returnClass = method.getReturnType();
				break;
			}
		}
		// 如果方法返回类型为空，直接返回
		if (returnClass == null) {
			return null;
		}

		if (returnClass instanceof Set) {
			// 集合 后续开发
			return resultObj;
		}
		if (returnClass instanceof List) {
			// 集合 后续开发
			return resultObj;

		}
		return mergeSignle(mappedStatement, boundSql, resultObj);
	}

	private static String maxPattern = ".*max\\(.*\\).*";

	@SuppressWarnings("unchecked")
	public static Object mergeSignle(MappedStatement mappedStatement, BoundSql boundSql, Object resultObj) {
		String sql = boundSql.getSql();
		boolean isMaxMatch = Pattern.matches(maxPattern, sql);
		if (isMaxMatch) {
			// 如果获取，max值
			ArrayList<Object> list = (ArrayList<Object>) resultObj;
			ArrayList<Object> newList = new ArrayList<Object>();
			newList.add(max(list));
			return newList;

		}

		return resultObj;
	}

	/**
	 * 获取最大值
	 * @param coll
	 * @return
	 */
	public static Object max(ArrayList<Object> coll) {

		if (coll == null || coll.isEmpty()) {
			return null;
		}

		if (coll.size() == 1) {
			return coll.get(0);
		}
		Object obj = coll.get(0);
		if (obj instanceof Long) {

			Iterator<Object> i = coll.iterator();
			Long candidate = Long.parseLong(i.next().toString());
			while (i.hasNext()) {
				Object nextObject = i.next();
				if (nextObject != null) {
					Long next = Long.parseLong(nextObject.toString());
					if (next.compareTo(candidate) > 0) {
						candidate = next;
					}
				}
			}
			return candidate;
		}
		if (obj instanceof Integer) {

			Iterator<Object> i = coll.iterator();
			Integer candidate = Integer.parseInt(i.next().toString());
			while (i.hasNext()) {
				Object nextObject = i.next();
				if (nextObject != null) {
					Integer next = Integer.parseInt(nextObject.toString());
					if (next.compareTo(candidate) > 0) {
						candidate = next;
					}
				}
			}
			return candidate;
		}

		return null;
	}
}
