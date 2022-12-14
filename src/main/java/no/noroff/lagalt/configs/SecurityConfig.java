package no.noroff.lagalt.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .sessionManagement().disable()
                .csrf().disable()
                // Enable security for http requests
                .authorizeHttpRequests(authorize -> authorize
                        // Everyone gets to access endpoints for fetching projects
                        .mvcMatchers(HttpMethod.GET, "/api/v1/projects/**").permitAll()
                        // Allows access to overview of documented API endpoints through swagger
                        .mvcMatchers(
                                "/v3/api-docs/**",
                                "/swagger-resources/configuration/ui",
                                "/swagger-resources",
                                "/swagger-resources/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**"
                        ).permitAll()
                        // Other endpoints may only be accessed by authenticated users
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt())
                .headers()
                .xssProtection()
                .and()
                .contentSecurityPolicy("script-src 'self'");
        return http.build();
    }
}
