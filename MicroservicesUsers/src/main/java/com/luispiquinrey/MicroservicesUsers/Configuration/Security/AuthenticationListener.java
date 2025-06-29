package com.luispiquinrey.MicroservicesUsers.Configuration.Security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationListener {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationListener.class);

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        logger.info("🟢 Successful login: {}", success.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failure) {
        logger.warn("🔴 Failed login: {}", failure.getAuthentication().getName());
        logger.warn("⚠️ Reason: {}", failure.getException().getMessage());
    }
}
