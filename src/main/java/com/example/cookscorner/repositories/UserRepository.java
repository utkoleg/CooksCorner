package com.example.cookscorner.repositories;


import com.example.cookscorner.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByActivationToken(String token);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    User findByPasswordResetToken(String token);
}
