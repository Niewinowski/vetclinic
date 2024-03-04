package com.niewhic.vetclinic.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
////                        .requestMatchers(req -> req.getServletPath().startsWith("/user")).permitAll()
////                        .requestMatchers(req -> req.getMethod().equals("GET")).hasAuthority(UserPermission.READ.name())
////                        .requestMatchers(req -> req.getMethod().equals("POST")
////                                || req.getMethod().equals("PUT")
////                                || req.getMethod().equals("PATCH")).hasAuthority(UserPermission.WRITE.name())
////                        .requestMatchers(req -> req.getMethod().equals("DELETE")).hasAuthority(UserPermission.DELETE.name())
////                        .requestMatchers(req -> req.getMethod().equals("GET")).authenticated()
//                        .anyRequest().authenticated()
//                )
//                .csrf(AbstractHttpConfigurer::disable)
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());
//        // TODO exception handling
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(req -> req.getServletPath().startsWith("/user")).permitAll()
                        .requestMatchers(req -> req.getMethod().equals("GET")).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(req -> req.getMethod().equals("POST")
                                || req.getMethod().equals("PUT")
                                || req.getMethod().equals("PATCH")
                                || req.getMethod().equals("DELETE")).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        // TODO exception handling
        return http.build();
    }


    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
                .passwordEncoder(passwordEncoder::encode)
                .password("user")
                .roles("USER") // Or .authorities("ROLE_USER") depending on your preference
                .build();

        UserDetails admin = User.withUsername("admin")
                .passwordEncoder(passwordEncoder::encode)
                .password("admin")
                .roles("ADMIN") // Or .authorities("ROLE_ADMIN") depending on your preference
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

}
