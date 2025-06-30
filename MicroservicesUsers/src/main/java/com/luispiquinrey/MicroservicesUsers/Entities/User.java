package com.luispiquinrey.MicroservicesUsers.Entities;

import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.luispiquinrey.MicroservicesUsers.Configuration.DatabaseConfig.RoleConverter;
import com.luispiquinrey.MicroservicesUsers.Configuration.Mask.Mask;
import com.luispiquinrey.MicroservicesUsers.Configuration.Mask.MaskSerializer;
import com.luispiquinrey.MicroservicesUsers.Enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User extends RepresentationModel<User> implements UserDetails {

    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_user")
    private Long id_user;

    @Column(name="username", nullable = false, unique = true)
    private String username;

    @Column(name="email")
    @Mask
    @JsonSerialize(using = MaskSerializer.class)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_USER'")
    @Convert(converter = RoleConverter.class)
    private Role role = Role.ROLE_USER;

    public User() {}

    public User(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    public User(Long id_user, String username, String email, String password) {
        this.id_user = id_user;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId_user() {
        return id_user;
    }

    public void setId_user(Long id_user) {
        this.id_user = id_user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String userToJson() throws JsonProcessingException{
        ObjectMapper mapper=new ObjectMapper();
        ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
        String json=writer.writeValueAsString(this);
        log.info("""
                \u001b[32m\ud83d\uded2 Product JSON formatted:
                """ + json + "\u001B[0m");
        return json;
    }
}