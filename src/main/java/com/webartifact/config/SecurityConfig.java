package com.webartifact.config;

import com.webartifact.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by trevorBye on 9/21/16.
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userProfileService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/javascript/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .httpBasic()
                    .and()
                .authorizeRequests()
                    .antMatchers("/index.html", "/sign-up.html","/login.html", "/", "/registeruser", "registeruser").permitAll()
                    .anyRequest().authenticated()
                    .and()
                .csrf().disable();


    }
}





















