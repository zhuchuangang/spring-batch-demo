package com.szss.demo.client;

import com.alibaba.druid.pool.DruidDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = TargetDBConfig.PACKAGE, sqlSessionFactoryRef = "targetSqlSessionFactory")
public class TargetDBConfig {
    static final String PACKAGE = "com.szss.demo.client.target";

    @Value("${datasource.target.url}")
    private String url;
    @Value("${datasource.target.username}")
    private String username;
    @Value("${datasource.target.password}")
    private String password;
    @Value("${datasource.target.driver-class-name}")
    private String driverClassName;
    @Value("${datasource.target.initialSize:20}")
    private Integer initialSize;
    @Value("${datasource.target.minIdle:20}")
    private Integer minIdle;
    @Value("${datasource.target.maxActive:50}")
    private Integer maxActive;

    @Bean(name = "dataSource")
    @Primary
    public DataSource targetDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(60000);
        dataSource.setValidationQuery("select 1");
        return dataSource;
    }

    @Bean(name = "targetTransactionManager")
    @Primary
    public DataSourceTransactionManager targetTransactionManager(@Qualifier("dataSource") DataSource sourceDataSource) {
        return new DataSourceTransactionManager(sourceDataSource);
    }

    @Bean(name = "targetSqlSessionFactory")
    @Primary
    public SqlSessionFactory targetSqlSessionFactory(@Qualifier("dataSource") DataSource sourceDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(sourceDataSource);
        return sessionFactory.getObject();
    }
}
