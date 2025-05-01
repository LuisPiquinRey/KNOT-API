package com.luispiquinrey.apiknot.RestController;


import com.luispiquinrey.apiknot.Entities.DTO.DTOUser;
import com.luispiquinrey.apiknot.Entities.User;
import com.luispiquinrey.apiknot.Service.ImplServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class RestControllerUser {

    @Autowired
    private final ImplServiceUser implServiceUser;

    public RestControllerUser(ImplServiceUser implServiceUser) {
        this.implServiceUser = implServiceUser;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody DTOUser dtoUser) {
        try {
            User newUser = new User.UserBuilder()
                    .email(dtoUser.getEmail())
                    .password(dtoUser.getPassword())
                    .username(dtoUser.getUsername())
                    .roles(List.of("ROLE_USER"))
                    .build();

            implServiceUser.createUser(newUser);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error creating user: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody String email) {
        try {
            implServiceUser.deleteUser(email);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error deleting user: " + e.getMessage());
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody DTOUser dtoUser) {
        try {
            User updatedUser = new User.UserBuilder()
                    .email(dtoUser.getEmail())
                    .password(dtoUser.getPassword())
                    .username(dtoUser.getUsername())
                    .build();

            implServiceUser.updateUser(updatedUser);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
        }
    }

    @PostMapping("/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody Map<String, String> payload) {
        try {
            String email = payload.get("email");
            String role = payload.get("role");

            User existingUser = implServiceUser.findByEmail(email);
            if (existingUser == null) {
                return ResponseEntity.badRequest().body("User not found");
            }
            if (role == null || role.isEmpty()) {
                return ResponseEntity.badRequest().body("Role cannot be null or empty");
            }

            List<String> currentRoles = existingUser.getRoles() != null ?
                    new ArrayList<>(existingUser.getRoles()) : new ArrayList<>();
            if (!currentRoles.contains(role)) {
                currentRoles.add(role);
            }

            User updatedUser = new User.UserBuilder()
                    .email(existingUser.getEmail())
                    .password(existingUser.getPassword())
                    .username(existingUser.getUsername())
                    .roles(currentRoles)
                    .build();

            implServiceUser.updateUser(updatedUser);

            return ResponseEntity.ok("Role added to user successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding role to user: " + e.getMessage());
        }
    }

    @GetMapping("/seeAll")
    public ResponseEntity<?> seeAllUsers() {
        try {
            return ResponseEntity.ok(implServiceUser.seeAllUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving users: " + e.getMessage());
        }
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        try {
            User resource = implServiceUser.findByEmail(email);
            if (resource != null) {
                resource.add(Link.of("https://localhost:8080/api/user/seeAll").withRel("See all users"));
                resource.add(Link.of("https://localhost:8080/api/user/update").withRel("Update user"));
                resource.add(Link.of("https://localhost:8080/api/user/delete").withRel("Delete user"));
                resource.add(Link.of("https://localhost:8080/api/user/addRoleToUser").withRel("Add role to user"));
                resource.add(Link.of("https://localhost:8080/api/user/login").withRel("Login user"));
                resource.add(Link.of("https://localhost:8080/api/user/create").withRel("Create user"));
                resource.add(Link.of("https://localhost:8080/api/user/findByEmail").withRel("Find user by email"));
                return ResponseEntity.ok(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving user: " + e.getMessage());
        }
    }
}

