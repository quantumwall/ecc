package dev.zykov.expatcalc.security;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;

import dev.zykov.expatcalc.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService oAuthUserService;
    private final UserService userService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable())
            .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth.requestMatchers("/login").permitAll().anyRequest().authenticated())
            .oauth2Login(login -> login.userInfoEndpoint(ue -> ue.userService(oAuthUserService))
                .successHandler(this::handleSuccessAuthentication))
            .build();
    }

    private void handleSuccessAuthentication(HttpServletRequest request, HttpServletResponse responce,
                                             Authentication authentication) throws IOException {
        var user = (CustomOauth2User) authentication.getPrincipal();
        userService.processPostLogin(user.getEmail());
        responce.sendRedirect("/");
    }

}
