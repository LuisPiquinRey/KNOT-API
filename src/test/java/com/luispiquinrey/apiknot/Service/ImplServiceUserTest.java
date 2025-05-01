package com.luispiquinrey.apiknot.Service;

import com.luispiquinrey.apiknot.Entities.User;
import com.luispiquinrey.apiknot.Repository.RepositoryUser;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ImplServiceUserTest {
    @Mock
    private RepositoryUser repositoryUser;

    @InjectMocks
    private ImplServiceUser serviceUser;

    private User user;

    @Test
    void findByEmail() {
        user = new User.UserBuilder()
                .username("luispiquinrey")
                .password("piquin.rey@gmail.com")
                .email("piquin.rey@gmail.com")
                .build();

        Mockito.when(repositoryUser.findByEmail("piquin.rey@gmail.com")).thenReturn(Optional.of(user));
        Mockito.when(repositoryUser.findByEmail("anonymous@gmail.com")).thenReturn(Optional.empty());

        assertEquals(user, serviceUser.findByEmail(user.getEmail()));

        assertThrows(EntityNotFoundException.class, () -> serviceUser.findByEmail("anonymous@gmail.com"));
        Mockito.verify(repositoryUser, Mockito.times(2)).findByEmail(Mockito.anyString());
    }
    @Test
    @DisplayName("Create user")
    void createUser() {
        user = new User.UserBuilder()
                .username("luispiquinrey")
                .password("securePassword123")
                .email("piquin.rey@gmail.com")
                .build();
        Mockito.when(repositoryUser.save(user)).thenReturn(user);
        User result = serviceUser.createUser(user);
        assertEquals(user, result);
        Mockito.verify(repositoryUser, Mockito.times(1)).save(user);
    }
    @Test
    @DisplayName("Delete user")
    void deleteUser(){
        user = new User.UserBuilder()
                .username("luispiquinrey")
                .password("securePassword123")
                .email("piquin.rey@gmail.com")
                .build();
        Mockito.when(repositoryUser.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        serviceUser.deleteUser(user.getEmail());
        Mockito.verify(repositoryUser, Mockito.times(1)).delete(user);
    }
    @Test
    @DisplayName("Delete user - Exception")
    void deleteUserWhenUserNotFound() {
        String email = "noexiste@gmail.com";

        Mockito.when(repositoryUser.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> serviceUser.deleteUser(email));
        Mockito.verify(repositoryUser, Mockito.times(1)).findByEmail(email);
        Mockito.verify(repositoryUser, Mockito.never()).delete(Mockito.any(User.class));
    }
    @Test
    @DisplayName("Update user")
    void updateUser(){
        user = new User.UserBuilder()
                .username("luispiquinrey")
                .password("securePassword123")
                .email("piquin.rey@gmail.com")
                .build();
        Mockito.when(repositoryUser.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        serviceUser.updateUser(user);
        Mockito.verify(repositoryUser, Mockito.times(1)).save(user);
        Mockito.verify(repositoryUser, Mockito.never()).delete(Mockito.any(User.class));
    }
    @Test
    @DisplayName("See all users")
    void seeAllUsers() {
        List<User> users = List.of(
                new User.UserBuilder()
                        .username("user1")
                        .password("password1")
                        .email("user1@example.com")
                        .build(),
                new User.UserBuilder()
                        .username("user2")
                        .password("password2")
                        .email("user2@example.com")
                        .build()
        );
        Mockito.when(repositoryUser.findAll()).thenReturn(users);
        List<User> result = serviceUser.seeAllUsers();
        assertEquals(users, result);
        Mockito.verify(repositoryUser, Mockito.times(1)).findAll();
    }
    @Test
    @DisplayName("Find by username")
    void findByUsername() {
        String username = "luispiquinrey";
        User user = new User.UserBuilder()
                .username(username)
                .password("securePassword123")
                .email("piquin.rey@gmail.com")
                .build();

        Mockito.when(repositoryUser.findByUsername(username)).thenReturn(Optional.of(user));

        User result = serviceUser.findByUsername(username);

        assertEquals(user, result);
        Mockito.verify(repositoryUser, Mockito.times(1)).findByUsername(username);
    }
}
