package com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.security;

import com.example.EnterPriseEventSolutions.EnterPriseEventSolutions.models.UserTipeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.security.SecureRandom;
import java.util.Arrays;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private Environment env;

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
        public boolean isTestEnvironment() {
            return Arrays.asList(env.getActiveProfiles()).contains("test");
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
                .antMatchers(HttpMethod.GET,"/api/users/me").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/users/me/").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/users/me").hasAnyRole(UserTipeEnum.ORGANIZATION.toString(),UserTipeEnum.CLIENT.toString(),UserTipeEnum.ADMIN.toString())
                .antMatchers(HttpMethod.GET,"/api/events").hasAnyRole(UserTipeEnum.CLIENT.toString(),UserTipeEnum.ADMIN.toString())
                .antMatchers(HttpMethod.GET,"/api/admin/**").hasRole(UserTipeEnum.ADMIN.toString())
                .antMatchers(HttpMethod.GET,"/api/clients/**").hasRole(UserTipeEnum.CLIENT.toString());
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }
}