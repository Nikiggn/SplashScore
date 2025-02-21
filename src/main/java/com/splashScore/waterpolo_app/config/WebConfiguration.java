package com.splashScore.waterpolo_app.config;

 import com.splashScore.waterpolo_app.security.SessionInterceptor;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 import org.springframework.security.web.SecurityFilterChain;
 import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
 import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
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

    @Bean // начин по който Spring Security разбира как да се прилага за нашето приложение
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // за да опиша endpoints, които ще бъдат свободно достъпвани
        // requestMatchers - достъп до даден endpoint
        // anyRequest - всияки заявки, които не съм изброил
        // .authenticated() - трябва да си аутентикиран
        http.
                authorizeHttpRequests(request -> request
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/register","/users/profile").permitAll() // Public Pages - да им се приложи Authentication
                        .anyRequest().authenticated())// Everything else need login
                .formLogin(form -> form
                        .loginPage("/login")
//                      .usernameParameter("email") // ако очаква емайл а не username (id = email "HTML")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/"));



        return http.build();
    }
}
