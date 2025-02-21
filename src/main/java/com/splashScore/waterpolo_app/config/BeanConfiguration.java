package com.splashScore.waterpolo_app.config;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.dialect.function.AvgFunction;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;

@Configuration
//Spring ще черпи някаква configuration
public class BeanConfiguration {


    @Bean
    // когато искам -|- Спринг искам да ми връща конкретна имплентация на този Беан
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }



    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }

    @Bean
    public Validator validator() {
        return Validation.byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();
    }
}
