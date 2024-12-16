package com.github.platform.core.gateway.infra.configuration.mybatis;

import com.github.platform.core.common.plugin.mybatis.ShardingInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * loan库动态数据源
 *
 * @author: yxkong
 * @date: 2021/6/3 4:26 下午
 * @version: 1.0
 */
@Configuration
@MapperScan(basePackages = {
        "com.github.platform.core.persistence.mapper"}, sqlSessionFactoryRef = "sqlSessionFactorySys")
public class SysDBConfiguration {

    @Resource(name = "sysDataSource")
    private DataSource dataSource;

    /**
     * 设置只走sys库的mapper
     * @return
     * @throws Exception
     */
    @Bean(name = "sqlSessionFactorySys")
    public SqlSessionFactory sqlSessionFactorySys() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        //添加分表插件
        ShardingInterceptor shardingInterceptor = new ShardingInterceptor();
        factoryBean.setPlugins(shardingInterceptor);
        factoryBean.setDataSource(dataSource);
        //设置映射器资源位置
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mybatis/**/*Mapper.xml"));
        //设置实体类别名包，告诉MyBatis去哪里查找实体类
        factoryBean.setTypeAliasesPackage("com.github..*.infra.domain.common.entity");

        return factoryBean.getObject();
    }

    @Bean(name = "sqlSessionTemplateSys")
    public SqlSessionTemplate sqlSessionTemplateSys() throws Exception {
        // 使用上面配置的Factory
        return new SqlSessionTemplate(sqlSessionFactorySys());
    }

}
