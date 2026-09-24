package com.helpdesk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.helpdesk.service.impl.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            CustomUserDetailsService userDetailsService,
            BCryptPasswordEncoder passwordEncoder) {

        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);

        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Login público
                        .requestMatchers("/login","/registro").permitAll()

                        // Inicio para cualquier usuario autenticado
                        .requestMatchers("/")
                        .hasAnyRole("ADMINISTRADOR", "TECNICO", "USUARIO")

                        // Tickets
                        .requestMatchers("/tickets/**")
                        .hasAnyRole("ADMINISTRADOR", "TECNICO", "USUARIO")

                        // Base de Conocimiento
                        .requestMatchers("/base-conocimiento/**")
                        .hasAnyRole("ADMINISTRADOR", "TECNICO", "USUARIO")

                        // Perfil
                        .requestMatchers("/perfil/**")
                        .hasAnyRole("ADMINISTRADOR", "TECNICO", "USUARIO")

                        // Dashboard
                        .requestMatchers("/dashboard/**")
                        .hasAnyRole("ADMINISTRADOR", "TECNICO")

                        // Usuarios
                        .requestMatchers("/usuarios/**")
                        .hasRole("ADMINISTRADOR")

                        // Administración
                        .requestMatchers("/admin/**")
                        .hasRole("ADMINISTRADOR")

                        // Recursos estáticos
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**"
                        ).permitAll()

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                )

                .authenticationProvider(
                        authenticationProvider(
                                new CustomUserDetailsService(),
                                passwordEncoder()
                        )
                );

        return http.build();
    }
}