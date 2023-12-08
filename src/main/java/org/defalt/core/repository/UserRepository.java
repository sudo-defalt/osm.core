package org.defalt.core.repository;

import org.defalt.core.entity.User;

import java.util.Optional;


public interface UserRepository extends AbstractEntityRepository<User> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber);
}
