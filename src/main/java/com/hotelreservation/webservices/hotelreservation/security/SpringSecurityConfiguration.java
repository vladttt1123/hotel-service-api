package com.hotelreservation.webservices.hotelreservation.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests(
                auth -> auth.anyRequest().authenticated());
        httpSecurity.httpBasic(withDefaults());
        httpSecurity.csrf().disable();
        return httpSecurity.build();
    }


}
