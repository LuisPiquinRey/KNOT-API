package com.luispiquinrey.apiknot.RestController;

import com.luispiquinrey.apiknot.Entities.DTO.DTOUser;
import com.luispiquinrey.apiknot.Entities.User;
import com.luispiquinrey.apiknot.Service.ImplServiceUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestControllerUser {

    @Autowired
    private final ImplServiceUser implServiceUser;

    RestControllerUser(ImplServiceUser implServiceUser) {
        this.implServiceUser = implServiceUser;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody DTOUser user) {
        try{
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setUsername(user.getUsername());
            newUser.setRoles(List.of("ROLE_USER"));
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
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            implServiceUser.updateUser(user);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
        }
    }
    @PostMapping("/addRoleToUser")
    public ResponseEntity<?> addRoleToUser(@RequestBody String email, @RequestBody String role) {
        try {
            User user=implServiceUser.findByEmail(email);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found");
            }
            if (role == null || role.isEmpty()) {
                return ResponseEntity.badRequest().body("Role cannot be null or empty");
            }
            user.setRoles(List.of(role));
            try{
                implServiceUser.updateUser(user);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error updating user: " + e.getMessage());
            }
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
            if(resource != null){
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
