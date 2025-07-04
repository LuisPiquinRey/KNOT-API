package com.luispiquinrey.MicroservicesUsers.Service.Implementation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.luispiquinrey.MicroservicesUsers.Entities.User;
import com.luispiquinrey.MicroservicesUsers.Exceptions.UserCreateException;
import com.luispiquinrey.MicroservicesUsers.Exceptions.UserDeleteException;
import com.luispiquinrey.MicroservicesUsers.Exceptions.UserUpdateException;
import com.luispiquinrey.MicroservicesUsers.Repository.RepositoryUser;
import com.luispiquinrey.MicroservicesUsers.Service.Interface.IServiceUser;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ImplServiceUser implements IServiceUser {

    @Autowired
    private RepositoryUser repositoryUser;

    @Autowired
    private PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(ImplServiceUser.class);

    ImplServiceUser(RepositoryUser repositoryUser, PasswordEncoder passwordEncoder) {
        this.repositoryUser = repositoryUser;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void deleteTargetById(Long id) throws UserDeleteException {
        try {
            if (!repositoryUser.existsById(id)) {
                logger.warn("Attempted to delete non-existent user with ID: {}", id);
                throw new UserDeleteException("Error deleting user: User with ID " + id + " does not exist.");
            }
            repositoryUser.deleteById(id);
            logger.info("User with ID {} deleted successfully", id);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage());
            throw new UserDeleteException("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public void updateTarget(User user) throws UserUpdateException {
        if (user.getId_user() == null) {
            logger.error("Update failed: User ID is null");
            throw new UserUpdateException("User ID cannot be null");
        }

        User existingUser = repositoryUser.findById(user.getId_user())
            .orElseThrow(() -> {
                logger.warn("Update failed: User with ID {} not found", user.getId_user());
                return new UserUpdateException("User not found");
            });

        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }

        try {
            repositoryUser.save(existingUser);
            logger.info("User {} updated successfully", user.getId_user());
        } catch (Exception e) {
            logger.error("Update error for user {}: {}", user.getId_user(), e.getMessage());
            throw new UserUpdateException("Database error during update");
        }
    }

    @Override
    public void createTarget(User user) throws UserCreateException {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new UserCreateException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new UserCreateException("Password cannot be empty");
        }
        if (repositoryUser.existsByUsername(user.getUsername())) {
            throw new UserCreateException("Username already exists");
        }
        if (user.getEmail() != null && repositoryUser.existsByEmail(user.getEmail())) {
            throw new UserCreateException("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repositoryUser.save(user);
    }

    @Override
    public User getUserOrThrow(Long id) throws EntityNotFoundException {
        return repositoryUser.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    @Override
    public List<User> findAllUsers() {
        return repositoryUser.findAll();
    }
}