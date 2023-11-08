package dev.zykov.expatcalc.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import dev.zykov.expatcalc.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(String name);

}
