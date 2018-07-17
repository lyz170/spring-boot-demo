package com.mytest.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * The SecurityConfig will:<br/>
 * (1) Require authentication to every URL in your application<br/>
 * (2) Generate a login form for you<br/>
 * (3) Allow the user with the Username user and the Password password to authenticate with form based authentication<br/>
 * (4) Allow the user to logout<br/>
 * (5) CSRF attack prevention<br/><br/>
 * (6) Session Fixation protection<br/>
 * (7) Security Header integration<br/>
 * - HTTP Strict Transport Security for secure requests<br/>
 * - X-Content-Type-Options integration<br/>
 * - Cache Control (can be overridden later by your application to allow caching of your static resources)<br/>
 * - X-XSS-Protection integration<br/>
 * - X-Frame-Options integration to help prevent Clickjacking<br/>
 * (8) Integrate with the following Servlet API methods<br/>
 * - HttpServletRequest#getRemoteUser()<br/>
 * - HttpServletRequest.html#getUserPrincipal()<br/>
 * - HttpServletRequest.html#isUserInRole(java.lang.String)<br/>
 * - HttpServletRequest.html#login(java.lang.String, java.lang.String)<br/>
 * - HttpServletRequest.html#logout()<br/>
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/index").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .and()
                .formLogin().loginPage("/login").failureUrl("/login-error");
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {

        // hasRole("USER") = hasAuthority("ROLE_USER")
        // https://stackoverflow.com/questions/19525380/difference-between-role-and-grantedauthority-in-spring-security
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user1")
                .password("{bcrypt}" + encoder.encode("user1"))
                .roles("ADMIN").build());
        manager.createUser(User.withUsername("user2")
                .password("{bcrypt}" + encoder.encode("user2"))
                .roles("USER").build());

        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
