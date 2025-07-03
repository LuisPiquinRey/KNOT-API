package com.luispiquinrey.KnotCommerce.Repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.luispiquinrey.KnotCommerce.DTOs.CategoryNode;

@Repository
public interface RepositoryCategoryNode extends Neo4jRepository<CategoryNode, Integer>{
	
}
