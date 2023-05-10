package org.canvas.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz.anyRequest().authenticated())
                .httpBasic(withDefaults());
        http.oauth2ResourceServer().jwt();
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()));
        return http.build();
    }
}
