package com.luispiquinrey.apiknot.Aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luispiquinrey.apiknot.Entities.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectUser {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @After("execution(* com.luispiquinrey.apiknot.Service.ImplServiceUser.createUser(..))")
    public void afterCreateUser(JoinPoint joinPoint) {
        if (joinPoint.getArgs().length > 0 && joinPoint.getArgs()[0] instanceof User user) {
            try {
                String userJson = objectMapper.writeValueAsString(user);
                System.out.println("✅ \u001B[32m User created: " + userJson + "\u001B[0m");
            } catch (JsonProcessingException e) {
                System.err.println("❌ Error serializing user: " + e.getMessage());
            }
        }
    }
}
