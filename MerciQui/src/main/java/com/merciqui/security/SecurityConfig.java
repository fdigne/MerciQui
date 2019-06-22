package com.merciqui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("laurence").password("3tcTa!csc").roles("USER");
        
    }
	
	
	
	
	/*
	 * public void configure(HttpSecurity http) throws Exception { http
	 * .headers().frameOptions().disable() .addHeaderWriter(new
	 * StaticHeadersWriter("X-FRAME-OPTIONS","SAMEORIGIN")); }
	 */
}
