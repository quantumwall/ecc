package dev.zykov.expatcalc.service;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import dev.zykov.expatcalc.entity.User;
import dev.zykov.expatcalc.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest request) throws OAuth2AuthenticationException {
        var oidcUser = super.loadUser(request);
        return processUser(oidcUser);
    }

    private OidcUser processUser(OidcUser oidcUser) {
        var email = oidcUser.getEmail();
        var name = (String) oidcUser.getAttribute("name");
        var user = userService.findByEmail(email);
        log.info("user is: {}", user.isEmpty());
        if (user.isEmpty()) {
            var userToSave = User.builder().email(email).name(name)
                .roles(Set.of(roleRepository.findByName("ROLE_USER").get())).balance(BigDecimal.ZERO).currency("")
                .active(true).build();
            log.info("user to save: {}");
            userService.create(userToSave);
        }

        return oidcUser;
    }

}
