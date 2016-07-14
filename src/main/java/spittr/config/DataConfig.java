package spittr.config;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:datasource.properties")
public class DataConfig implements TransactionManagementConfigurer {

  @Autowired
  private SessionFactory sessionFactory;

  @Resource
  private Environment env;

  //TODO can change to BasicDataSource and JNDI
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName(env.getRequiredProperty("datasource.driverClassName"));
    ds.setUrl(env.getRequiredProperty("datasource.databaseurl"));
    //TODO should move the database and hibernate configuration outside
    ds.setUsername(env.getRequiredProperty("datasource.username"));
    ds.setPassword(env.getRequiredProperty("datasource.password"));
    return ds;
  }

  //TODO can change to JTA
  //TODO differenct between Transactionmagers
  // annotationDrivenTransactionManager is abstract method of TransactionManagementConfigurer interface,
  // so maybe no need to use @bean
  public PlatformTransactionManager annotationDrivenTransactionManager() {
    System.out.println(sessionFactory);
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory);
    return transactionManager;
  }

  //LocalSessionFactoryBean produce SessionFactory
  @Bean
  public SessionFactory sessionFactoryBean() {
    try {
      LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
      lsfb.setDataSource(dataSource());
      lsfb.setPackagesToScan("spittr.entity");
      Properties props = new Properties();
      props.setProperty("dialect", env.getRequiredProperty("datasource.dialect"));
      props.setProperty("show_sql", env.getRequiredProperty("datasource.show_sql"));
      props.setProperty("format_sql", env.getRequiredProperty("datasource.show_sql"));
      props.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.EhCacheRegionFactory");
      props.setProperty("hibernate.cache.use_structured_entries", "true");
      props.setProperty("hibernate.cache.use_second_level_cache", "true");
      props.setProperty("hibernate.cache.use_query_cache", "true");
      lsfb.setHibernateProperties(props);
      lsfb.afterPropertiesSet();
      //  can also just return lsfb, and it will automatically getobejct
      SessionFactory object = lsfb.getObject();
      return object;
    } catch (IOException e) {
      return null;
    }
  }


/*  @Bean
  public SpringLiquibase liquibase() {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
    liquibase.setDataSource(dataSource());
    return liquibase;
  }*/
}


/*@Configuration
public class DataConfig {

  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
            .setType(EmbeddedDatabaseType.H2)
            .addScript("schema.sql")
            .build();
  }

  @Bean
  public JdbcOperations jdbcTemplate(DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}*/