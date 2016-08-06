package spittr.config;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;

import spittr.config.RootConfig.WebPackage;

@Configuration
@Import(DataConfig.class)
@ImportResource("classpath:messaging.xml")
@ComponentScan(basePackages={"spittr"}, 
    excludeFilters={
        @Filter(type=FilterType.CUSTOM, value=WebPackage.class)
    })
public class RootConfig {
  public static class WebPackage extends RegexPatternTypeFilter {
    public WebPackage() {
      super(Pattern.compile("spittr\\.(web|api)"));
    }    
  }


  @Bean
  public Logger log(){
    return LoggerFactory.getLogger("spittr");
  }

}


//@Configuration
//@ComponentScan(basePackages={"spitter"},
//        excludeFilters={
//                @Filter(type=FilterType.ANNOTATION, value=EnableWebMvc.class)
//        })
//public class RootConfig {
//}
