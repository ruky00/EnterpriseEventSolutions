package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Security;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.Models.UserTipeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.lang.reflect.Method;
import java.security.SecureRandom;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    private RepositoryUserDetailService userDetailService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Public pages
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/users").permitAll()
                .antMatchers(HttpMethod.GET,"/api/users/me").hasAnyRole(UserTipeEnum.ORGANIZATION.toString(),UserTipeEnum.CLIENT.toString(),UserTipeEnum.ADMIN.toString())
                .antMatchers(HttpMethod.PUT,"/api/users/me/").hasAnyRole(UserTipeEnum.ORGANIZATION.toString(),UserTipeEnum.CLIENT.toString(),UserTipeEnum.ADMIN.toString())
                .antMatchers(HttpMethod.DELETE,"/api/users/me").hasAnyRole(UserTipeEnum.ORGANIZATION.toString(),UserTipeEnum.CLIENT.toString());



        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}