package dev.zykov.expatcalc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import dev.zykov.expatcalc.security.oidc.CustomOidcUserService;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;

    @Bean
    SecurityFilterChain filter(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/authorize").permitAll();
            auth.anyRequest().authenticated();
        });
        http.oauth2Login(login -> {
            login.defaultSuccessUrl("/authorize", true);
            login.userInfoEndpoint(ui -> ui.oidcUserService(customOidcUserService));
        });
        return http.build();
    }
}
