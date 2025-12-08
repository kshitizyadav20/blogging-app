package com.Kshitiz.blog.config;

import com.Kshitiz.blog.security.CustomUserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 1️⃣ UserDetailsService bean
    @Bean
    public CustomUserDetailService customUserDetailService() {
        return new CustomUserDetailService();
    }

    // 2️⃣ Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 3️⃣ Authentication Provider (this replaces old configure(AuthenticationManagerBuilder))
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(customUserDetailService());
        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    // 4️⃣ Authentication Manager (needed for login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 5️⃣ HTTP Security configuration (this replaces old configure(HttpSecurity))
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider()) // <-- IMPORTANT
                .httpBasic(httpBasic -> {}); // basic auth popup

        return http.build();
    }
}
