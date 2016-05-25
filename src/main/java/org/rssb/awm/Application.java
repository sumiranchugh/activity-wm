package org.rssb.awm;

import org.activiti.rest.security.BasicAuthenticationProvider;
import org.activiti.spring.boot.RestApiAutoConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.interceptor.JamonPerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Created by Sumiran Chugh on 3/23/2016.
 *
 * @copyright atlas
 */
@Configuration
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value =
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

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
        crlf.setIncludeClientInfo(true);
        crlf.setIncludeQueryString(true);
        crlf.setIncludePayload(true);
        return crlf;
    }

    @Bean
    public CustomizableTraceInterceptor customizableTraceInterceptor() {
        CustomizableTraceInterceptor cti = new CustomizableTraceInterceptor();
        cti.setEnterMessage("Entering method '" + CustomizableTraceInterceptor.PLACEHOLDER_METHOD_NAME + "(" + CustomizableTraceInterceptor.PLACEHOLDER_ARGUMENTS + ")' of class [" +
                CustomizableTraceInterceptor.PLACEHOLDER_TARGET_CLASS_NAME + "]");
        cti.setExitMessage("Exiting method '" + CustomizableTraceInterceptor.PLACEHOLDER_METHOD_NAME + "' of class [" +
                CustomizableTraceInterceptor.PLACEHOLDER_TARGET_CLASS_NAME + "] took " + CustomizableTraceInterceptor.PLACEHOLDER_INVOCATION_TIME + "ms.");
        return cti;
    }

    @Bean
    public Advisor traceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * org.rssb.awm..*.*(..)) && !execution(public * org.rssb.awm.confg..*.*(..))");

        return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
    }

    @Bean
    public JamonPerformanceMonitorInterceptor jamonPerformanceMonitorInterceptor() {
        return new JamonPerformanceMonitorInterceptor();
    }

    @Bean
    public Advisor performanceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public * org.rssb.awm..*.*(..)) && !execution(public * org.rssb.awm.confg..*.*(..))");
        return new DefaultPointcutAdvisor(pointcut, jamonPerformanceMonitorInterceptor());
    }

}
