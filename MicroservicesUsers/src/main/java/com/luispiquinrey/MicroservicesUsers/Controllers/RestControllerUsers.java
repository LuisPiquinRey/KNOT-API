package com.luispiquinrey.MicroservicesUsers.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luispiquinrey.MicroservicesUsers.Entities.User;
import com.luispiquinrey.MicroservicesUsers.Exceptions.UserCreateException;
import com.luispiquinrey.MicroservicesUsers.Exceptions.UserDeleteException;
import com.luispiquinrey.MicroservicesUsers.Exceptions.UserUpdateException;
import com.luispiquinrey.MicroservicesUsers.Service.IServiceUser;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class RestControllerUsers {
    @Autowired
    private final IServiceUser iServiceUser;

    RestControllerUsers(IServiceUser iServiceUser){
        this.iServiceUser=iServiceUser;
    }
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        try{
            iServiceUser.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body("User created successfully!");
        }catch(UserCreateException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        try{
            User user=iServiceUser.getUserOrThrow(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body(user.userToJson());
        }catch(EntityNotFoundException | JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }
    @GetMapping("/findAllUsers")
    public ResponseEntity<?> getAllUsers(){
        try{
            return ResponseEntity.ok(iServiceUser.findAllUsers());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving all users: " + e.getMessage());
        }
    }
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        try{
            iServiceUser.deleteUserById(id);
            return ResponseEntity.status(HttpStatus.OK)
                .body("Product with the id: " + id + " correctly deleted");
        }catch(UserDeleteException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        try{
            iServiceUser.updateUser(user);
            return ResponseEntity.status(HttpStatus.OK)
                .body("User with id: " + user.getId_user() + "correctly updated");
        }catch(UserUpdateException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
        }
    }
}
