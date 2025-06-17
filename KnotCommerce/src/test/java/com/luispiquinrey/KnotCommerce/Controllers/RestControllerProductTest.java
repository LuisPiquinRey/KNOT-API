package com.luispiquinrey.KnotCommerce.Controllers;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Entities.Product.Product;
import com.luispiquinrey.KnotCommerce.Service.IServiceProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestControllerProduct.class)
class RestControllerProductTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IServiceProduct iServiceProduct;

    @Test
    @DisplayName("Create product")
    void createProduct() throws Exception {
        String productJson = """
            {
                "type": "noPerishable",
                "name": "Adidas samba",
                "price": 120.32,
                "stock": 5,
                "available": true,
                "categories": [
                    {
                        "id_Category": 1,
                        "name": "Sports"
                    }
                ],
                "description": "Classic sports shoes"
            }
        """;

        mockMvc.perform(post("/createProduct")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson))
                .andExpect(status().isCreated());
    }
}
