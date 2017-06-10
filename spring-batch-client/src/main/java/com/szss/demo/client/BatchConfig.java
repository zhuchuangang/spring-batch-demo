package com.szss.demo.client;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Autowired
  private StepBuilderFactory stepBuilderFactory;


  @Bean
  BatchConfigurer configurer(@Qualifier("dataSource") DataSource dataSource) {
    return new DefaultBatchConfigurer(dataSource);
  }


  @Bean
  public JobLauncher jobLauncher(){
    SimpleJobLauncher jobLauncher=new SimpleJobLauncher();
    SimpleAsyncTaskExecutor executor=new SimpleAsyncTaskExecutor();
    jobLauncher.setTaskExecutor(executor);
    return jobLauncher;
  }

//  @Bean
//  public JobRepository jobRepository(@Qualifier("dataSource") DataSource dataSource,
//      @Qualifier("targetTransactionManager")DataSourceTransactionManager transactionManager)
//      throws Exception {
//    JobRepositoryFactoryBean jobRepository=new JobRepositoryFactoryBean();
//    jobRepository.setDataSource(dataSource);
//    jobRepository.setDatabaseType("mysql");
//    jobRepository.setTransactionManager(transactionManager);
//    return jobRepository.getObject();
//  }

//  @Bean
//  public MyBatisCursorItemReader reader(@Qualifier("sourceSqlSessionFactory") SqlSessionFactory sourceFactory) {
//    MyBatisCursorItemReader reader = new MyBatisCursorItemReader();
//    reader.setSqlSessionFactory(sourceFactory);
//    reader.setQueryId("findAll");
//    return reader;
//  }

  @Bean
  @StepScope
  public MyBatisPagingItemReader reader(
      @Qualifier("sourceSqlSessionFactory") SqlSessionFactory sourceFactory) {
    MyBatisPagingItemReader reader = new MyBatisPagingItemReader();
    reader.setSqlSessionFactory(sourceFactory);
    reader.setQueryId("com.szss.demo.client.source.UserMapper.findPage");
    reader.setPageSize(2);
    return reader;
  }

//  @Bean
//  @JobScope
//  public MyBatisCursorItemReader reader(@Qualifier("sourceSqlSessionFactory") SqlSessionFactory sourceFactory,
//      @Value("#{jobParameters[user_id]}")Long userId) {
//    System.out.println("======================"+userId+"======================");
//    MyBatisCursorItemReader reader = new MyBatisCursorItemReader();
//    reader.setSqlSessionFactory(sourceFactory);
//    reader.setQueryId("findUserById");
//    Map params=new HashMap();
//    params.put("id",userId);
//    reader.setParameterValues(params);
//
//    return reader;
//  }


  @Bean
  public UserItemProcessor processor() {
    return new UserItemProcessor();
  }

//  @Bean
//  public JdbcBatchItemWriter<User> writer() {
//    JdbcBatchItemWriter<User> writer = new JdbcBatchItemWriter<User>();
//    writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<User>());
//    writer.setSql("INSERT INTO my_user (id,username, password) VALUES (:id, :username, :password)");
//    writer.setDataSource(dataSource);
//    return writer;
//  }

  @Bean
  public MyBatisBatchItemWriter writer(
      @Qualifier("targetSqlSessionFactory") SqlSessionFactory targetFactory) {
    MyBatisBatchItemWriter<User> writer = new MyBatisBatchItemWriter<>();
    writer.setSqlSessionFactory(targetFactory);
    writer.setStatementId("saveUser");
    return writer;
  }

  @Bean
  public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
    return jobBuilderFactory.get("importUserJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
  }

  @Bean
  public Step step1(AbstractPagingItemReader reader, MyBatisBatchItemWriter writer) {
    return stepBuilderFactory.get("step1")
        .<User, User>chunk(10)
        .reader(reader)
        .processor(processor())
        .writer(writer)
        .build();
  }

}
