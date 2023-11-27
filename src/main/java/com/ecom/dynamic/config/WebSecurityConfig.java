package com.ecom.dynamic.config;

import com.ecom.dynamic.util.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;

    public WebSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // other configurations
                .authorizeRequests()
                .antMatchers("/products/*").authenticated()
                // other endpoint configurations
                .and()
                .formLogin() // Enable form-based login
                .loginPage("/login") // Specify the login page URL
                .permitAll() // Allow everyone to access the login page
                .and()
                .logout() // Enable logout
                .logoutSuccessUrl("/login?logout") // Redirect to the login page after logout
                .permitAll() // Allow everyone to access the logout URL
                .and()
                .csrf().disable() // Disable CSRF for simplicity, you might want to enable it in a real-world scenario
                .httpBasic()
                .and()
                .apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
