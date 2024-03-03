package com.crm.wm.controllers;

import com.crm.wm.dto.ProductDTO;
import com.crm.wm.entities.Product;
import com.crm.wm.repository.ProductRepositoryJdbc;
import com.crm.wm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepositoryJdbc productRepositoryJdbc;

    // Other autowired dependencies...

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/list")
    public List<ProductDTO> getAllProductsUsingJdbc() {
        return productRepositoryJdbc.findAll();
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<String> createProductUsingJdbc(@RequestBody Product product) {
        int result = productRepositoryJdbc.insert(product);
        if (result == 1) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create product");
        }
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/list/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        ProductDTO product = productRepositoryJdbc.findById(productId);
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO updatedProduct) {
        // Perform validation or other checks as needed

        // Set the product ID from the path variable into the updated product
        updatedProduct.setProductID(productId);

        // Update the product in the database
        productRepositoryJdbc.update(updatedProduct);

        // Fetch the updated product from the database
        ProductDTO product = productRepositoryJdbc.findById(productId);

        return ResponseEntity.ok(product);
    }

    // Other CRUD operations or custom endpoints as needed
}

