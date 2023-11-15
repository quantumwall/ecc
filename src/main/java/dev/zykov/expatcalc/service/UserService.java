package dev.zykov.expatcalc.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.zykov.expatcalc.entity.User;
import dev.zykov.expatcalc.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User create(User user) {
        return userRepository.save(user);
    }
}
