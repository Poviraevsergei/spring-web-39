package com.tms.config;

import com.tms.controllers.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableMethodSecurity //Включает настойку авторизации через аннотации @PreAuthorize
@Configuration
public class SpringSecurityConfiguration {
    private final CustomUserDetailService customUserDetailsService;

    // Общие настройки Security приложения
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(new AntPathRequestMatcher("/security/**", "GET")).hasRole("ADMIN")
                                .requestMatchers(new AntPathRequestMatcher("/security/registration", "POST")).permitAll()
                        .anyRequest().authenticated())
                .userDetailsService(customUserDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

//TODO: 3. Java Web Token
    //TODO: Генерации JWT
    //TODO: Настройка секьюрити
//TODO: 4. настройка Swagger
//TODO: 5. Проект
//TODO: 6. Params links

















