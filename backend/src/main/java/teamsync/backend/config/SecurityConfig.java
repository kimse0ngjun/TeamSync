package teamsync.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(login -> login.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/health",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/v3/api-docs/**",
                                "/api/auth/**",
                                "/api-docs/swagger-config",
                                "/ws/**"
                        ).permitAll()
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/api/team/**").authenticated()
                        .requestMatchers("/api/organization/**").authenticated()
                        .requestMatchers("/api/meeting-rooms/**").authenticated()
                        .requestMatchers("/api/team/{teamId}/owner/**").hasRole("OWNER")
                        .requestMatchers("/api/team/{teamId}/members/**").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers("/api/team/{teamId}/role/**").hasAnyRole("OWNER", "ADMIN")
                        .requestMatchers("/api/rooms/**").hasAnyRole("OWNER", "ADMIN", "MEMBER")
                        .requestMatchers("/api/chat/**").hasAnyRole("OWNER", "ADMIN", "MEMBER")
                        .requestMatchers("/api/calendar/**").hasAnyRole("OWNER", "ADMIN", "MEMBER")
                        .requestMatchers("/api/activities/**").hasAnyRole("OWNER", "ADMIN", "MEMBER")
                        .requestMatchers("/api/summary/**").hasAnyRole("OWNER", "ADMIN", "MEMBER")
                        .anyRequest().authenticated()  // 나머지 모든 요청은 인증 필요
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://127.0.0.1:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
