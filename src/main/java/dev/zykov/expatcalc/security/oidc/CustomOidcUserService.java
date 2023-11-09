package dev.zykov.expatcalc.security.oidc;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import dev.zykov.expatcalc.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    
    private final UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest request) throws OAuth2AuthenticationException {
        var user = super.loadUser(request);
        return processUser(user);
    }

    private OidcUser processUser(OidcUser user) {
        return user;
    }

    
}
