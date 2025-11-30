package com.school.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.services.impl.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oauth2UserService;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfig()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        /* rotas públicas */
                        .requestMatchers("/", "/login", "/oauth2/**", "/api/auth/**").permitAll()
                        //.requestMatchers("/api/auth/me").authenticated()
                        /* swagger  */
                        .requestMatchers("/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html").permitAll()
                        /* tudo o resto precisa de autorização */
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(oidcUserService())  // Google
                                .userService(oauth2UserService) // GitHub / outros
                        )
                        .defaultSuccessUrl("/api/auth/me", true)
                        .successHandler((req, resp, auth) -> {
                            resp.setContentType("application/json");
                            resp.setStatus(HttpServletResponse.SC_OK);
                            new ObjectMapper().writeValue(resp.getOutputStream(),
                                    Map.of("message", "Authenticated",
                                            "user", auth.getName()));
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }

    /* Google (OIDC) */
    private OidcUserService oidcUserService() {
        OidcUserService delegate = new OidcUserService();
        delegate.setOidcUserMapper(this::buildOidcUser);
        return delegate;
    }

    private OidcUser buildOidcUser(OAuth2UserRequest userRequest, OidcUserInfo userInfo) {
        OidcUser oidcUser = new OidcUserService().loadUser((OidcUserRequest) userRequest);
        OAuth2User principal = oauth2UserService.loadUser(userRequest, oidcUser);
        return (principal instanceof OidcUser) ? (OidcUser) principal
                : new DefaultOidcUser(
                                principal.getAuthorities(),
                                oidcUser.getIdToken(),
                                oidcUser.getUserInfo(),
                                "email");
    }

    private CorsConfigurationSource corsConfig() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:3000")); // React, Angular...
        cfg.setAllowedMethods(List.of("*"));
        cfg.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return src;
    }
}
