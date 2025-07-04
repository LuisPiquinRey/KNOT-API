package com.luispiquinrey.MicroservicesUsers.Service.Interface;
import java.util.List;

import com.luispiquinrey.MicroservicesUsers.Entities.User;
import com.luispiquinrey.common_tools.Service.ICrudService;

import jakarta.persistence.EntityNotFoundException;

public interface IServiceUser extends ICrudService<User, Long>{
    User getUserOrThrow(Long id) throws EntityNotFoundException;
    List<User> findAllUsers();
}
