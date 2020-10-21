package com.merciqui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import java.io.FileReader;
import java.util.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private String USERNAME;
	private String PASSWORD;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		FileReader reader=new FileReader("/root/MerciQui/merciqui.properties");
		Properties p=new Properties();
		p.load(reader);
		this.USERNAME = p.getProperty("USERNAME");
		this.PASSWORD = p.getProperty("PASSWORD");

        auth
            .inMemoryAuthentication()
                .withUser(USERNAME).password(PASSWORD).roles("USER");
        
    }
	
	
	
	
	/*
	 * public void configure(HttpSecurity http) throws Exception { http
	 * .headers().frameOptions().disable() .addHeaderWriter(new
	 * StaticHeadersWriter("X-FRAME-OPTIONS","SAMEORIGIN")); }
	 */
}
