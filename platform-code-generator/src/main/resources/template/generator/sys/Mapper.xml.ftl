<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${mapperPackage}.${entityName}Mapper">

	<!--数据库字段与属性映射-->
	<resultMap type="${domainPackage}.common.entity.${entityName}Base" id="BaseResultMap">
		${resultMap}
    </resultMap>
	<!--通用查询字段封装-->
    <sql id="BaseColumnList">
		${baseColumnList}
    </sql>

	<insert id="insert" useGeneratedKeys="true"  keyProperty="id" parameterType="${domainPackage}.common.entity.${entityName}Base">
		${insertSql}
	</insert>
	
	<update id="updateById" parameterType="${domainPackage}.common.entity.${entityName}Base">
		${updateSql}
	</update>
	<select id="findById" resultMap="BaseResultMap" parameterType="java.lang.${pkColumnType}">
		select <include refid="BaseColumnList" /> from ${tableName} t where t.${pkColumnName}=<#noparse>#{</#noparse>${pkLowerColumnName}}
	</select>
	<select id="findByIds" resultMap="BaseResultMap" parameterType="java.lang.${pkColumnType}">
		select <include refid="BaseColumnList" /> from ${tableName} t
		<where>
			<foreach item="id"  collection="ids" open="${pkColumnName}  in (" separator="," close=")" >
				<#noparse>#{</#noparse>${pkLowerColumnName}}
			</foreach>
		</where>
		limit 200
	</select>
	<select id="findListBy" resultMap="BaseResultMap" parameterType="${domainPackage}.common.entity.${entityName}Base">
		${listSql}
	</select>
	<select id="findListByCount" resultType="java.lang.Long"  parameterType="${domainPackage}.common.entity.${entityName}Base">
		${listCountSql}
	</select>
	<delete id="deleteById" parameterType="java.lang.${pkColumnType}">
		delete from ${tableName} where ${pkColumnName} = <#noparse>#{</#noparse>${pkLowerColumnName}}
	</delete>
	<delete id="deleteByIds" parameterType="java.lang.${pkColumnType}">
		<!--没有传入数据抛异常，不会全删-->
		<if test="ids != null and ids.length>0">
			delete from ${tableName}
			<where>
				<foreach item="id"  collection="ids" open="${pkColumnName}  in (" separator="," close=")" >
					<#noparse>#{</#noparse>${pkLowerColumnName}}
				</foreach>
			</where>
		</if>
	</delete>
</mapper>