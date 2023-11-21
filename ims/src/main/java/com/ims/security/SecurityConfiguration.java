package com.ims.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security.
 * The config allows all Client-related requests to pass without authentication but requires
 * authentication for all other endpoints and passes them through the custom TokenFilter.
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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

