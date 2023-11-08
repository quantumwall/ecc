package dev.zykov.expatcalc.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.zykov.expatcalc.entity.User;

public interface UserRepository extends CrudRepository<User, Long> {
    
    Optional<User> findByEmail(String email);

}
