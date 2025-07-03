package com.luispiquinrey.KnotCommerce.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luispiquinrey.KnotCommerce.Entities.Category;
import com.luispiquinrey.KnotCommerce.Service.Facade.FacadeServiceCategory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Tag(name = "CRUD Repository for Categories", description = "CRUD REST API to CREATE, UPDATE, FETCH AND DELETE categories")
@RestController
public class RestControllerCategory {

    private static final Logger logger = LoggerFactory.getLogger(RestControllerCategory.class);

    @Autowired
    private final FacadeServiceCategory facadeServiceCategory;

    public RestControllerCategory(FacadeServiceCategory facadeServiceCategory) {
        this.facadeServiceCategory = facadeServiceCategory;
    }

    @Operation(summary = "Create category", description = "Endpoint to create new category", method = "POST", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category object to be created", required = true))
    @ApiResponse(responseCode = "201", description = "HTTP Status CREATED")
    @PostMapping("/createCategory")
    public ResponseEntity<?> createCategory(@Valid @RequestBody Category category,
            org.springframework.validation.BindingResult binding) {
        if (binding.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            binding.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(sb.toString().trim());
        }
        try {
            facadeServiceCategory.createTarget(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(category.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Update category", description = "Endpoint to update an existing category", method = "PUT", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Category object to be updated", required = true))
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @PutMapping("/updateCategory")
    public ResponseEntity<?> updateCategory(@RequestBody Category category) {
        try {
            facadeServiceCategory.updateTarget(category);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Category with id: " + category.getId_Category() + " correctly updated");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete category", description = "Endpoint to delete a category by ID", method = "DELETE", parameters = {
            @Parameter(name = "id", description = "ID of the category to delete", required = true)
    })
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        try {
            facadeServiceCategory.deleteTargetById(id);
            return ResponseEntity.status(HttpStatus.OK).body("Category with the id: " + id + " correctly deleted");
        } catch (EntityNotFoundException e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Get category by ID", description = "Endpoint to retrieve category details by ID", method = "GET", parameters = {
            @Parameter(name = "id_Category", description = "ID of the category to retrieve", required = true)
    })
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/getCategoryById/{id_Category}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer id_Category) {
        try {
            Category category = facadeServiceCategory.getCategoryOrThrow(id_Category);
            return ResponseEntity.ok(category.toString());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " + e.getMessage());
        }
    }

    @Operation(summary = "List all categories", description = "Endpoint to retrieve all categories in the system", method = "GET")
    @ApiResponse(responseCode = "200", description = "HTTP Status OK")
    @GetMapping("/findAllCategories")
    public ResponseEntity<?> getAllCategories() {
        try {
            return ResponseEntity.ok(facadeServiceCategory.findAllCategories());
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error retrieving all categories: " + e.getMessage());
        }
    }
}
