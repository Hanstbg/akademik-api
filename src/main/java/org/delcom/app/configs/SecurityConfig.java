package org.delcom.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new UsernameNotFoundException("Not used");
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/**")
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/matakuliah/**").hasRole("DOSEN")
                .requestMatchers(HttpMethod.PUT, "/api/matakuliah/**").hasRole("DOSEN")
                .requestMatchers(HttpMethod.DELETE, "/api/matakuliah/**").hasRole("DOSEN")
                .requestMatchers(HttpMethod.GET, "/api/matakuliah/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/tugas/**").hasRole("DOSEN")
                .requestMatchers(HttpMethod.PUT, "/api/tugas/**").hasRole("DOSEN")
                .requestMatchers(HttpMethod.DELETE, "/api/tugas/**").hasRole("DOSEN")
                .requestMatchers(HttpMethod.GET, "/api/tugas/**").authenticated()
                .requestMatchers("/api/enrollment/**").hasRole("MAHASISWA")
                .requestMatchers(HttpMethod.POST, "/api/submission/**").hasRole("MAHASISWA")
                .requestMatchers(HttpMethod.PUT, "/api/submission/*/nilai").hasRole("DOSEN")
                .requestMatchers(HttpMethod.GET, "/api/submission/**").authenticated()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}