package com.luispiquinrey.KnotCommerce.Repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.luispiquinrey.KnotCommerce.DTOs.ProductNode;

@Repository
public interface RepositoryProductNode extends Neo4jRepository<ProductNode, Long>{
}
