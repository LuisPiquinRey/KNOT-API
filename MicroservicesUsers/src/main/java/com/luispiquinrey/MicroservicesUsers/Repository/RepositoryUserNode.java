package com.luispiquinrey.MicroservicesUsers.Repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.luispiquinrey.MicroservicesUsers.DTOs.UserNode;

public interface RepositoryUserNode extends Neo4jRepository<UserNode, Long>{
    
}
