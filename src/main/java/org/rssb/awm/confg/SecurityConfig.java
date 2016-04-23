package org.rssb.awm.confg;

import org.activiti.engine.IdentityService;
import org.rssb.awm.security.BimsAuthUserDetailsService;
import org.rssb.awm.security.BimsPreAuthenticatedProcessingFilter;
import org.rssb.awm.security.HttpEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
@ConditionalOnClass(name = {"org.activiti.rest.service.api.RestUrls", "org.springframework.web.servlet.DispatcherServlet"})
@Order(99)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    IdentityService identityService;
    @Value("${bims.url}")
    private String bimsUrl;
    @Value("${bims.validatetoken}")
    private String bimsValidateUrl;
    @Value("${client.id}") // registeredUrl
    private String clientId;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().antMatchers("/","/login*","/401*","/404*","/resources/**","/views/**","/users",
                //"/getLoggedInUser",
                "/home**")
                .permitAll().anyRequest().authenticated().and().
                addFilter(abstractPreAuthenticatedProcessingFilter
                        (authenticationManager())).exceptionHandling().authenticationEntryPoint(getAuthEntryPoint()).and()
                .logout().logoutUrl("/logout").invalidateHttpSession(true).addLogoutHandler(logoutHandler()).logoutSuccessHandler(logoutHandler());

    }
    //https://zims.in:13254/geoloc/rest/updateVersionForMobile/9958695007/4

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new PreAuthenticatedAuthenticationProvider());
    }

    @Bean
    public Filter abstractPreAuthenticatedProcessingFilter(AuthenticationManager manager){
        AbstractPreAuthenticatedProcessingFilter preAuthenticatedProcessingFilter = new BimsPreAuthenticatedProcessingFilter();
        preAuthenticatedProcessingFilter.setAuthenticationManager(manager);
        return preAuthenticatedProcessingFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        List<AuthenticationProvider> providerList = new ArrayList<>();
        providerList.add(authenticationProvider());
        return new ProviderManager(providerList);
    }

    @Bean(name = "authprovider")
    public AuthenticationProvider authenticationProvider(){
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService(authenticationUserDetailsService());
        return provider;
    }


    @Bean
    public AuthenticationUserDetailsService authenticationUserDetailsService(){
        return new BimsAuthUserDetailsService(bimsValidateUrl,identityService);
    }

    /*@Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }*/
    public AuthenticationEntryPoint getAuthEntryPoint() {
        return new HttpEntryPoint(bimsUrl,clientId);
    }


    public MyLogoutHandler logoutHandler(){
        return new MyLogoutHandler();
    }

    public static class MyLogoutHandler implements LogoutHandler,LogoutSuccessHandler {

        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

            response.sendRedirect("home");
        }

        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            if(SecurityContextHolder.getContext().getAuthentication()!=null) {
                (new SecurityContextLogoutHandler()).logout(request, response, SecurityContextHolder.getContext().getAuthentication());
            }
        }
    }

}
