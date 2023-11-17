package dev.zykov.expatcalc.service;

import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.zykov.expatcalc.entity.User;
import dev.zykov.expatcalc.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User %s not found".formatted(email)));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), null,
                user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList());
    }

}
