package com.maven.configuration;

import com.maven.util.JtwAuthenticationEntryPoint;
import com.maven.util.JwtAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity //it is used to enable login page
@EnableMethodSecurity //it is use to secure endpoint
@AllArgsConstructor //it is used to create the constructor of injected class
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtRequestFilter;
    private final JtwAuthenticationEntryPoint point;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        return security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("/api/service/**").permitAll()
                                    .requestMatchers(HttpMethod.OPTIONS).permitAll()
                                    .requestMatchers("/admin/*").hasAuthority("ADMIN")
                                    .requestMatchers("/user/*").hasAuthority("STUDENT")
                                    .anyRequest()
                                    .authenticated();
                        }
                ).exceptionHandling(ex -> {
                    ex.authenticationEntryPoint(point);
                })
                .sessionManagement(
                        session -> {
                            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                        }
                ).addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){


        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
