package com.splashScore.waterpolo_app.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity
public class WebConfiguration implements WebMvcConfigurer {
    @Bean // начин по който Spring Security разбира как да се прилага за нашето приложение
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // за да опиша endpoints, които ще бъдат свободно достъпвани
        // requestMatchers - достъп до даден endpoint
        // anyRequest - всияки заявки, които не съм изброил
        // .authenticated() - трябва да си аутентикиран
        http.
                authorizeHttpRequests(request -> request
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/register").permitAll() // Public Pages - да им се приложи Authentication
                        .anyRequest().authenticated())// Everything else need login
                .formLogin(form -> form
                        .loginPage("/login")
//                      .usernameParameter("email") // ако очаква емайл а не username (id = email "HTML")
                        .defaultSuccessUrl("/",true)
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                        .logoutSuccessUrl("/"));


        return http.build();
    }
}
