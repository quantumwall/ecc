package dev.zykov.expatcalc.rest.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizeController {

    @GetMapping("/authorize")
    public Map<String, Object> get(@AuthenticationPrincipal OAuth2User user) {
        return user.getAttributes();
    }
}
