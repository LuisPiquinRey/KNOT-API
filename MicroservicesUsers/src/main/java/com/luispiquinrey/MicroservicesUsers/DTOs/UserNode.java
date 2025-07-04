package com.luispiquinrey.MicroservicesUsers.DTOs;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("User")
public class UserNode {
    @Id
    private Long id_User;

    public UserNode() {
    }

    public UserNode(Long id_User) {
        this.id_User = id_User;
    }

    public Long getId_User() {
        return id_User;
    }

    public void setId_User(Long id_User) {
        this.id_User = id_User;
    }
}
