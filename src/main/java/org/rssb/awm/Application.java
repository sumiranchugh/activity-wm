package org.rssb.awm;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
/**
 * Created by Sumiran Chugh on 3/23/2016.
 *
 * @copyright atlas
 */
@Configuration
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
