package com.luispiquinrey.apiknot.Service;


import com.luispiquinrey.apiknot.Entities.User;
import java.util.List;



public interface IServiceUser{
    public User findByEmail(String email);
    public User findByUsername(String username);
    public User createUser(User user);
    public void deleteUser(String email);
    public void updateUser(User user);
    public List<User> seeAllUsers();
}
