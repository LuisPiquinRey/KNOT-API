package com.luispiquinrey.KnotCommerce.Repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.luispiquinrey.KnotCommerce.DTOs.CategoryNode;

public interface RepositoryCategoryNode extends Neo4jRepository<CategoryNode, Integer>{
	
}
