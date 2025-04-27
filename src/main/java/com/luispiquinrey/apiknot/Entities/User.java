package com.luispiquinrey.apiknot.Entities;
import java.lang.annotation.Annotation;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "Users")
public class User extends RepresentationModel<User> implements Cloneable{

    @Id
    @Email
    @NotNull
    @JsonProperty("email")
    @Column(unique = true)
    private String email;

    @NotNull
    @NotEmpty
    @JsonIgnore
    @JsonProperty("password")
    @Column(length = 60)
    private String password;

    @NotNull
    @NotEmpty
    @JsonProperty("username")
    @Column(length = 20)
    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty("roles")
    @Column(name = "role")
    private List<String> roles;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("createdAt")
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty("products")
    private List<Product> products;

    public User(){}

    public User(String password, String username, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

    public User(List<String> roles, Date createdAt, List<Product> products, String username, String password, String email) {
        this.roles = roles;
        this.createdAt = createdAt;
        this.products = products;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", roles=" + roles +
                ", createdAt=" + createdAt +
                ", products=" + products +
                '}';
    }
    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

    public static User fromJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, User.class);
    }

}