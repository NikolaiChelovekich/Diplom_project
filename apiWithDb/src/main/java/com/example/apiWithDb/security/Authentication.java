package com.example.apiWithDb.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class Authentication {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager()
    {
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        UserDetails userOne = users
                .username("maxim")
                .password("kaloed")
                .roles("USER")
                .build();

        UserDetails userTwo = users
                .username("mikola")
                .password("daun")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userOne, userTwo);

    }

}
