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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(basePackages = SourceDBConfig.PACKAGE, sqlSessionFactoryRef = "sourceSqlSessionFactory")
public class SourceDBConfig {
    static final String PACKAGE = "com.szss.demo.client.source";

    @Value("${datasource.source.url}")
    private String url;
    @Value("${datasource.source.username}")
    private String username;
    @Value("${datasource.source.password}")
    private String password;
    @Value("${datasource.source.driver-class-name}")
    private String driverClassName;
    @Value("${datasource.source.initialSize:20}")
    private Integer initialSize;
    @Value("${datasource.source.minIdle:20}")
    private Integer minIdle;
    @Value("${datasource.source.maxActive:50}")
    private Integer maxActive;

    @Bean(name = "sourceDataSource")
    public DataSource sourceDataSource() {
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

    @Bean(name = "sourceTransactionManager")
    public DataSourceTransactionManager sourceTransactionManager(@Qualifier("sourceDataSource") DataSource sourceDataSource) {
        return new DataSourceTransactionManager(sourceDataSource);
    }

    @Bean(name = "sourceSqlSessionFactory")
    public SqlSessionFactory sourceSqlSessionFactory(@Qualifier("sourceDataSource") DataSource sourceDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(sourceDataSource);
        return sessionFactory.getObject();
    }
}
