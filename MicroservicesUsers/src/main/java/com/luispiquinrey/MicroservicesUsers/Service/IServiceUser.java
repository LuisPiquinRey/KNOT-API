package com.luispiquinrey.MicroservicesUsers.Service;
import java.util.List;

import com.luispiquinrey.MicroservicesUsers.Entities.User;
import com.luispiquinrey.MicroservicesUsers.Exceptions.UserCreateException;
import com.luispiquinrey.MicroservicesUsers.Exceptions.UserDeleteException;
import com.luispiquinrey.MicroservicesUsers.Exceptions.UserUpdateException;

import jakarta.persistence.EntityNotFoundException;

public interface IServiceUser {
    void deleteUserById(Long id) throws UserDeleteException;
    void updateUser(User user) throws UserUpdateException;
    void createUser(User user) throws UserCreateException;
    User getUserOrThrow(Long id) throws EntityNotFoundException;
    List<User> findAllUsers();
}
