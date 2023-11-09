package dev.zykov.expatcalc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import dev.zykov.expatcalc.security.oidc.CustomOidcUserService;
import dev.zykov.expatcalc.service.UserService;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;
    private final CustomOidcUserService customOidcUserService;

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
        http.oauth2Login(login -> {
            login.defaultSuccessUrl("/authorize", true);
        });
//        http.oauth2Login(login -> login.userInfoEndpoint(ui -> ui.oidcUserService(customOidcUserService)));
        return http.build();
    }
}
