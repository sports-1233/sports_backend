package com.sport.backend.global.config;

//import com.sport.backend.global.jwt.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

//    private final AuthenticationFilter authenticationFilter;

    String[] whiteList = new String[]{
            "/v3/api-docs/**",
            "/swagger-ui/**", 
            "/swagger-ui.html",
            "/webjars/**",
            "/api/v3/api-docs/**",
            "/api/swagger-ui/**",
            "/api/swagger-ui.html", 
            "/api/webjars/**",
            "/v1/member/**",
    };

    // Security 6.x는 기본적으로 X-Frame-Options: DENY (H2 console, iframe 필요한 경우 설정해야 함)
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(whiteList).permitAll()
                        .anyRequest().authenticated())
//                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        ;
        return http.build();
    }
}
