package com.luispiquinrey.KnotCommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import com.luispiquinrey.KnotCommerce.Entities.Product.NoPerishableProduct;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;

@JsonTest
class JsonProductTest {
    @Autowired
    private JacksonTester<Product> json;

    @Test
    void testSerialize() throws Exception {
        Product product = new NoPerishableProduct(true, 1L, "Adidas Samba", 23.2,
            "Classic shoes", 20, null,  "2 years");
        var jsonContent = json.write(product);
        assertEquals(
            jsonContent.getJson().replaceAll("\\s", ""),
            product.productToJson().replaceAll("\\s", "")
        );
    }
}
