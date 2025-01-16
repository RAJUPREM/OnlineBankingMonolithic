package com.Bank.OnlineBanking.JwtUtility;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
   
    @Autowired
    CustomUserDetailsService userDetailsService;

    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//    	http.csrf().disable()
//        .authorizeHttpRequests(auth -> auth
//            .requestMatchers("/api/auth/**").permitAll() // Public endpoints
//            .requestMatchers("/user/**").hasRole("USER") // Admin-only endpoints
//            .requestMatchers("/account/**","/transaction/**","/schedule/**").hasAnyRole("USER", "ADMIN") // User endpoints
//            .anyRequest().authenticated() // All other endpoints require authentication
//        )
//        .httpBasic(); // or use JWT filter
//
//        return http.build();
    	
    	System.out.println("I am here in securityConfiggggggg");
    	
    	return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                .requestMatchers("/account/**","/transaction/**","/schedule/**")
                .hasAuthority("ROLE_ADMIN")
//                .requestMatchers("/userown/**")
//                .hasAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder())
//                .and()
//                .build();
//    }
    
//    @Bean
//    public GrantedAuthoritiesMapper authoritiesMapper() {
//        return authorities -> authorities.stream()
//            .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().replace("ROLE_", "")))
//            .collect(Collectors.toList());
//    }
}

