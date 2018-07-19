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
                .antMatchers("/user/**").hasRole("ADMIN") // Admin
                .antMatchers("/user/A1").hasRole("A1") // 大型客车
                .antMatchers("/user/A2").hasRole("A2") // 牵引车
                .antMatchers("/user/A3").hasAnyRole("A1", "A3") // 城市公交车
                .antMatchers("/user/B1").hasAnyRole("A1", "A2", "B1") // 中型客车
                .antMatchers("/user/B2").hasAnyRole("A1", "A2", "B2") // 大型货车
                .antMatchers("/user/C1").hasAnyRole("A1", "A2", "A3", "B1", "B2", "C1") // 小型汽车
                .antMatchers("/user/C2").hasAnyRole("A1", "A2", "A3", "B1", "B2", "C1", "C2") // 小型自动挡汽车
                .antMatchers("/user/C3").hasAnyRole("A1", "A2", "A3", "B1", "B2", "C1", "C3") // 低速载货汽车
                .antMatchers("/user/C4").hasAnyRole("A1", "A2", "A3", "B1", "B2", "C1", "C3", "C4") // 三轮车
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
        // ADMIN
        manager.createUser(User.withUsername("admin").password("{bcrypt}" + encoder.encode("admin"))
                .roles("ADMIN").build());
        // A1
        manager.createUser(User.withUsername("a1").password("{bcrypt}" + encoder.encode("a1"))
                .roles("A1").build());
        // A2
        manager.createUser(User.withUsername("a2").password("{bcrypt}" + encoder.encode("a2"))
                .roles("A2").build());
        // A3
        manager.createUser(User.withUsername("a3").password("{bcrypt}" + encoder.encode("a3"))
                .roles("A3").build());
        // B1
        manager.createUser(User.withUsername("b1").password("{bcrypt}" + encoder.encode("b1"))
                .roles("B1").build());
        // B2
        manager.createUser(User.withUsername("b2").password("{bcrypt}" + encoder.encode("b2"))
                .roles("B2").build());
        // C1
        manager.createUser(User.withUsername("c1").password("{bcrypt}" + encoder.encode("c1"))
                .roles("C1").build());
        // C2
        manager.createUser(User.withUsername("c2").password("{bcrypt}" + encoder.encode("c2"))
                .roles("C2").build());
        // C3
        manager.createUser(User.withUsername("c3").password("{bcrypt}" + encoder.encode("c3"))
                .roles("C3").build());
        // C4
        manager.createUser(User.withUsername("c4").password("{bcrypt}" + encoder.encode("c4"))
                .roles("C4").build());

        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
