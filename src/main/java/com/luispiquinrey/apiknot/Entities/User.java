package com.luispiquinrey.apiknot.Entities;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Table(name = "Users")
@XmlRootElement(name = "user")
@XmlType(propOrder = {"email", "password", "username"})
public class User extends RepresentationModel<User> implements Cloneable, Serializable{

    @Id
    @Email
    @NotNull
    @JsonProperty("email")
    @Column(unique = true)
    @XmlTransient
    private String email;

    @NotNull
    @NotEmpty
    @JsonIgnore
    @JsonProperty("password")
    @Column(length = 60)
    @XmlTransient
    private String password;

    @NotNull
    @NotEmpty
    @JsonProperty("username")
    @Column(length = 20)
    @XmlTransient
    private String username;

    @ElementCollection(fetch = FetchType.EAGER)
    @JsonProperty("roles")
    @Column(name = "role")
    @XmlTransient
    private List<String> roles;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("createdAt")
    @Column(name = "created_at")
    @XmlTransient
    private Date createdAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty("products")
    @XmlTransient
    private List<Product> products;

    private User(UserBuilder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.username =  builder.username;
        this.roles = builder.roles;
        this.createdAt = (Date) builder.createdAt;
        this.products = builder.products;
    }
    public User() {}
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public String getUsername() {
        return username;
    }
    public List<String> getRoles() {
        return roles;
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
    public void marshal() throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(User.class);
        Marshaller mar= context.createMarshaller();
        mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        mar.marshal(this,new File("./book.xml"));
    }

    public static class UserBuilder implements Builder<User> {
        private String email;
        private String password;
        private String username;
        private List<String> roles;
        private java.util.Date createdAt;
        private List<Product> products;

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder roles(List<String> roles) {
            this.roles = roles;
            return this;
        }

        public UserBuilder createdAt(java.util.Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserBuilder products(List<Product> products) {
            this.products = products;
            return this;
        }

        @Override
        public User build() {
            return new User(this);
        }
    }
}