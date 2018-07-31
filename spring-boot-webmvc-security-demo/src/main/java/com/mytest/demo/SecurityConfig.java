package com.mytest.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // Authorize Requests
                .antMatchers("/index").permitAll() // permit all
                .antMatchers("/a1/**").hasRole("A1") // who has access to /a1/.. url
                .antMatchers("/a2/**").hasRole("A2") // who has access to /a2/.. url
                .antMatchers("/a3/**").hasAnyRole("A1", "A3") // who has access to /a3/.. url
                .antMatchers("/b1/**").hasAnyRole("A1", "A2", "B1") // who has access to /b1/.. url
                .antMatchers("/b2/**").hasAnyRole("A1", "A2", "B2") // who has access to /b2/.. url
                .antMatchers("/c1/**").hasAnyRole("A1", "A2", "A3", "B1", "B2", "C1") // who has access to /c1/.. url
                .antMatchers("/c2/**").hasAnyRole("A1", "A2", "A3", "B1", "B2", "C1", "C2") // who has access to /c2/.. url
                .antMatchers("/c3/**").hasAnyRole("A1", "A2", "A3", "B1", "B2", "C1", "C3") // who has access to /c3/.. url
                .antMatchers("/c4/**").hasAnyRole("A1", "A2", "A3", "B1", "B2", "C1", "C3", "C4") // who has access to /c4/.. url
                // .anyRequest().authenticated() // any other request need to authenticate
                .and() // AND
                .formLogin() // login as form
                .loginPage("/login") // login url (default is login page with framework)
                // .defaultSuccessUrl("/index") // login success url (default is index)
                .failureUrl("/login-error") // login fail url
                // .and() // AND
                // .logout() // logout config
                // .logoutUrl("/logout") // logout url (default is logout)
                // .logoutSuccessUrl("/index") // logout success url (default is login)
        ;
    }
}