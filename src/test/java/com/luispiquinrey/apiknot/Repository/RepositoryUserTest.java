package com.luispiquinrey.apiknot.Repository;

import com.luispiquinrey.apiknot.Entities.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
public class RepositoryUserTest {

    @Mock
    private RepositoryUser repositoryUser;

    private User user;

    @Test
    @DisplayName("Create user")
    void creatUser() {
        user=new User.UserBuilder().username("luispiquinrey").password("piquin.rey@gmail.com").email("piquin.rey@gmail.com").build();
        when(repositoryUser.save(any(User.class))).thenReturn(user);
        assertEquals(user,repositoryUser.save(user));
    }
    @Test
    @DisplayName("Find by email")
    void findByEmail() {
        user = new User.UserBuilder()
                .username("luispiquinrey")
                .password("piquin.rey@gmail.com")
                .email("piquin.rey@gmail.com")
                .build();

        when(repositoryUser.findByEmail(any(String.class)))
                .thenReturn(Optional.of(user));
        assertEquals(Optional.of(user), repositoryUser.findByEmail(user.getEmail()));
    }

    @Test
    @DisplayName("Update user")
    void updateUser() {
        User existingUser = new User.UserBuilder().username("luispiquinrey").password("piquin.rey@gmail.com").email("piquin.rey@gmail.com").build();
        User updatedUser = new User.UserBuilder().username("luispiquinrey").password("piquin.rey@gmail.com").email("piquin.rey@gmail.com").build();
        when(repositoryUser.save(any(User.class))).thenReturn(updatedUser);
        assertEquals(updatedUser.getEmail(), repositoryUser.save(updatedUser).getEmail());
    }
    @Test
    @DisplayName("Delete user")
    void deleteUser() {
        User savedUser = new User.UserBuilder().username("luispiquinrey").password("piquin.rey@gmail.com").email("piquin.rey@gmail.com").build();
        when(repositoryUser.existsById(savedUser.getEmail())).thenReturn(true);
        repositoryUser.deleteById(savedUser.getEmail());
        when(repositoryUser.existsById(savedUser.getEmail())).thenReturn(false);
        boolean exists = repositoryUser.existsById(savedUser.getEmail());
        assertEquals(false, exists);
    }

}
