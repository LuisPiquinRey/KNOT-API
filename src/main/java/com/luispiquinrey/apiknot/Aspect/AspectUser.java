package com.luispiquinrey.apiknot.Aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.luispiquinrey.apiknot.Entities.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectUser {

    @After("execution(* com.luispiquinrey.apiknot.service.impl.ImplServiceUser.*(..))")
    public void afterCreateUser(JoinPoint joinPoint) {
        if (joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof User) {
            User user = (User) joinPoint.getArgs()[0];
            try {
                System.out.println("User created: " + user.toJson());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
