package com.luispiquinrey.apiknot.Service;


import com.luispiquinrey.apiknot.Entities.Product;

public interface  IServiceProduct {
    public Product createProduct(Product product);
    public Product updateProduct(Product product);
    public void deleteProduct(Integer product_id);
    public Product findProductById(Integer product_id);
    public Iterable<Product> findAllProducts();
}
