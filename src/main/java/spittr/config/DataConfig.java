package spittr.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
//@PropertySource("classpath:/com/soundsystem/app.properties")
public class DataConfig implements TransactionManagementConfigurer {

  @Inject
  private SessionFactory sessionFactory;

  //TODO can change to BasicDataSource and JNDI
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource ds = new DriverManagerDataSource();
    ds.setDriverClassName("com.mysql.jdbc.Driver");
    ds.setUrl("jdbc:mysql://localhost:3306/spittr");
    //TODO should move the database and hibernate configuration outside
    ds.setUsername("root");
    ds.setPassword("13421221497");
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
      props.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
      lsfb.setHibernateProperties(props);
      lsfb.afterPropertiesSet();
      //  can also just return lsfb, and it will automatically getobejct
      SessionFactory object = lsfb.getObject();
      return object;
    } catch (IOException e) {
      return null;
    }
  }
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