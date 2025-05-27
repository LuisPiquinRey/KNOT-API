package com.luispiquinrey.KnotCommerce.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;

import jakarta.transaction.Transactional;

@Repository
public interface RepositoryProduct extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products WHERE available = true", nativeQuery = true)
    List<Product> findAvailableProducts();

    @Query("SELECT p FROM Product p JOIN p.categories c WHERE c.name = :categoryName")
    List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p WHERE p.stock < :stockThreshold")
    List<Product> findLowStockProducts(@Param("stockThreshold") int stockThreshold);

    @Query("SELECT p FROM Product p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<Product> findByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice);

    @Modifying
    @Transactional
    @Query("DELETE FROM Product p WHERE :category MEMBER OF p.categories")
    void deleteByCategory(@Param("category") Category category);

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.stock = :stock WHERE p.id = :id")
    void updateStock(@Param("id") Long id, @Param("stock") int stock);
}

