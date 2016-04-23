package org.rssb.awm;

import org.activiti.rest.security.BasicAuthenticationProvider;
import org.activiti.spring.boot.RestApiAutoConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
/**
 * Created by Sumiran Chugh on 3/23/2016.
 *
 * @copyright atlas
 */
@Configuration
@ComponentScan(excludeFilters = {
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value =
        RestApiAutoConfiguration.SecurityConfiguration.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                value = SecurityAutoConfiguration.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                value = BasicAuthenticationProvider.class)})
@EnableAutoConfiguration(exclude = {org.activiti.spring.boot.RestApiAutoConfiguration.SecurityConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class, BasicAuthenticationProvider.class})
public class Application extends SpringBootServletInitializer {


    public static void main(String args[]) {

        new SpringApplicationBuilder(Application.class).run(args);
    }

@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        // Customize the application or call application.sources(...) to add sources
        // Since our example is itself a @Configuration class we actually don't
        // need to override this method.
        return application.sources(Application.class);
    }


}
