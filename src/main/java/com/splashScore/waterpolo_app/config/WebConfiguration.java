package com.splashScore.waterpolo_app.config;

 import com.splashScore.waterpolo_app.security.SessionInterceptor;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
  import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {


    private final SessionInterceptor sessionInterceptor;

    @Autowired
    public WebConfiguration(SessionInterceptor sessionInterceptor) {
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/img/**");
    }
}
