package com.example.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(value = "methodSecurity")
public class MethodSecurity {

    @Value("${test.hasAuthority}")
    String hasAuthority;

    @Value("${test.hasAnyAuthority}")
    String hasAnyAuthority;

    @Value("${test.hasRole}")
    String hasRole;

    @Value("${test.hasAnyRole}")
    String hasAnyRole;

    @Bean
    public String hasAuthority(){
        return hasAuthority;
    }

    @Bean
    public String[] hasAnyAuthority(){
        return hasAnyAuthority.split(",");
    }

    @Bean
    public String hasRole(){
        return hasRole;
    }

    @Bean
    public String[] hasAnyRole(){
        return hasAnyRole.split(",");
    }
}
