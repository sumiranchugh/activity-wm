package org.rssb.awm.confg;

/**
 * Created by Sumiran Chugh on 3/30/2016.
 *
 * @copyright atlas
 */
/*@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/scripts/", "classpath:/styles/",
            "classpath:/img/", "classpath:/fonts/" ,"classpath:/components/"};
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(interceptorAdapter()).addPathPatterns("/login*","/home*","/views*//**");
    }
    @Bean
    public HandlerInterceptorAdapter interceptorAdapter(){
        return new ViewInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if (!registry.hasMappingForPattern("*//**")) {
            registry.addResourceHandler("*//**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }
    }
}*/
