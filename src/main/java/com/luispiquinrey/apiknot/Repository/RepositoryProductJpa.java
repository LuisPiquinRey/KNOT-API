package com.luispiquinrey.apiknot.Repository;

import com.luispiquinrey.apiknot.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryProductJpa extends JpaRepository<Product, Integer> {
    public void deleteById(Integer product_id);
}

