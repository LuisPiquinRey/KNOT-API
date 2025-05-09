package com.luispiquinrey.apiknot.Service;

import com.luispiquinrey.apiknot.Entities.User;
import com.luispiquinrey.apiknot.Repository.RepositoryUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ImplServiceUser implements IServiceUser {

    private static final Logger logger = LoggerFactory.getLogger(ImplServiceUser.class);

    private final RepositoryUser repositoryUser;

    @Autowired
    public ImplServiceUser(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @Override
    @Transactional
    @Cacheable(value = "userCache", key = "#email")
    public User findByEmail(String email) {
        validateEmailOrUsername(email);
        return repositoryUser.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    @Transactional
    @CachePut(value = "userCache", key = "#user.email")
    public User createUser(User user) {
        validateUserFields(user);
        try{
            User savedUser = repositoryUser.save(user);
            return savedUser;
        }catch(Exception e){}
        throw new EntityNotFoundException("User not found with email: " + user.getEmail());
    }


    @Override
    @Transactional
    @CacheEvict(value = "userCache", key = "#user.email")
    public void deleteUser(String email) {
        validateEmailOrUsername(email);
        Optional<User> user = repositoryUser.findByEmail(email);
        if (user.isPresent()) {
            repositoryUser.delete(user.get());
            logger.info("User deleted: {}", user.get());
        } else {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
    }

    @Override
    @Transactional
    @CachePut(value = "userCache", key = "#user.email")
    public void updateUser(User user) {
        validateUserFields(user);
        Optional<User> existingUser = repositoryUser.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            repositoryUser.save(user);
            logger.info("User updated: {}", user);
        } else {
            throw new EntityNotFoundException("User not found with email: " + user.getEmail());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> seeAllUsers() {
        return repositoryUser.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "userCache", key = "#username")
    public User findByUsername(String username) {
        validateEmailOrUsername(username);
        return repositoryUser.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + username));
    }

    private void validateUserFields(User user) {
        if (user == null) throw new IllegalArgumentException("User cannot be null");
        if (user.getEmail() == null || user.getEmail().isEmpty())
            throw new IllegalArgumentException("Email cannot be null or empty");
        if (user.getPassword() == null || user.getPassword().isEmpty())
            throw new IllegalArgumentException("Password cannot be null or empty");
        if (user.getUsername() == null || user.getUsername().isEmpty())
            throw new IllegalArgumentException("Username cannot be null or empty");
    }

    private void validateEmailOrUsername(String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty");
        }
    }
}

