package com.luispiquinrey.KnotCommerce.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luispiquinrey.KnotCommerce.Entities.Product.Product;

@Repository
public interface RepositoryProduct extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE available = true", nativeQuery = true)
    List<Product> findAvailableProducts();

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p WHERE p.stock < :stockThreshold")
    List<Product> findLowStockProducts(@Param("stockThreshold") int stockThreshold);
}

