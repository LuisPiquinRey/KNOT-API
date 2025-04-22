package com.luispiquinrey.apiknot.Service;

import java.util.List;
import java.util.Optional;

import com.luispiquinrey.apiknot.Entities.User;

import com.luispiquinrey.apiknot.Repository.RepositoryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ImplServiceUser implements IServiceUser {

    @Autowired
    private final RepositoryUser repositoryUser;

    public ImplServiceUser(RepositoryUser repositoryUser) {
        this.repositoryUser = repositoryUser;
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        validateEmailOrUsername(email);
        return repositoryUser.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    @Transactional
    public void createUser(User user) {
        validateUserFields(user);
        repositoryUser.save(user);
        System.out.println("\u001B[32mUser created: " + user.toString() + "\u001B[0m");
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        validateEmailOrUsername(email);
        Optional<User> user = repositoryUser.findByEmail(email);
        if (user.isPresent()) {
            repositoryUser.delete(user.get());
            System.out.println("\u001B[31mUser deleted: " + user.toString() + "\u001B[0m");
        } else {
            throw new EntityNotFoundException("User not found with email: " + email);
        }
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        validateUserFields(user);
        Optional<User> existingUser = repositoryUser.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            repositoryUser.save(user);
            System.out.println("\u001B[33mUser updated: " + user.toString() + "\u001B[0m");
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
