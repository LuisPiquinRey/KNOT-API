package com.luispiquinrey.MicroservicesUsers.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luispiquinrey.MicroservicesUsers.Entities.User;

@Repository
public interface RepositoryUser extends JpaRepository<User,Long>{
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
