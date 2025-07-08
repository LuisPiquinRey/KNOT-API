package com.luispiquinrey.MicroservicesUsers.Configuration.Security.Filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.luispiquinrey.MicroservicesUsers.Configuration.Security.CustomServiceUser;
import com.luispiquinrey.MicroservicesUsers.Utils.JWTManager;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter{

    @Autowired
    private JWTManager jwtManager;

    @Autowired
    private CustomServiceUser customServiceUser;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt=jwtManager.getJwtFromHeader(request);
            String username=jwtManager.getUsernameFromJWT(jwt);
            if (jwtManager.verifyTokenJwt(jwt)){
                UserDetails userDetails=customServiceUser.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication=
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        return path.startsWith("/user/login");
    }
    
}
