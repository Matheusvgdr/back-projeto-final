package com.projetos.backbibliotecafinal.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class WebSecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> {
//                    authorize.requestMatchers(
//                                    AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
//                                    AntPathRequestMatcher.antMatcher("/swagger-ui.html"),
//                                    AntPathRequestMatcher.antMatcher("/v3/**"),
//                                    AntPathRequestMatcher.antMatcher("/h2-console/**")
//
//                            ).permitAll()
//                            .requestMatchers(AntPathRequestMatcher.antMatcher("/usuario/**")).permitAll()
//                            .requestMatchers(AntPathRequestMatcher.antMatcher("/biblioteca/**")).permitAll()
//                            .requestMatchers(AntPathRequestMatcher.antMatcher("/livro/**")).permitAll()
//                            .requestMatchers(AntPathRequestMatcher.antMatcher("/importacao")).permitAll()
//                            .requestMatchers(AntPathRequestMatcher.antMatcher("/autores/**")).permitAll()
//
//                            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/clientes")).permitAll()
                            authorize.anyRequest().permitAll();
                })
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // h2-console
                .addFilter(new JWTAuthenticationFilter(authenticationManager)) //autenticacao
                .addFilter(new JWTAuthorizationFilter(authenticationManager)) //autorizacao
                .authenticationManager(authenticationManager)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}


