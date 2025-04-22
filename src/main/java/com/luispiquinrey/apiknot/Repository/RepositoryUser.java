package com.luispiquinrey.apiknot.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.luispiquinrey.apiknot.Entities.User;
import java.util.Optional;

@Repository
public interface RepositoryUser extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}

