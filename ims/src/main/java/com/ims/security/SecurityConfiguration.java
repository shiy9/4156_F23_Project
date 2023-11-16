package com.ims.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Autowired
  private TokenFilter tokenFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable() // Disable CSRF since token-based auth
            .authorizeRequests()
            .antMatchers("/client/login", "/client/register", "/client/clienttestdelete")
            .permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
  }
}

