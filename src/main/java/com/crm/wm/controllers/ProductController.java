package com.crm.wm.controllers;

import com.crm.wm.dto.CustomerDTO;
import com.crm.wm.dto.CustomerProductAssociationRequest;
import com.crm.wm.dto.ProductDTO;
import com.crm.wm.entities.Customer;
import com.crm.wm.entities.CustomerType;
import com.crm.wm.entities.Municipality;
import com.crm.wm.entities.Product;
import com.crm.wm.repository.ProductRepositoryJdbc;
import com.crm.wm.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/products")

public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductRepositoryJdbc productRepositoryJdbc;

    // Other autowired dependencies...

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream().map(ProductDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }


    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        Product product = new Product();
        product.setProductID(productDTO.getProductID());
        product.setProductName(productDTO.getProductName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        if (productDTO.getMunicipality() != null) { // Check if municipality is not null
            // Create a new Municipality entity and set its ID
            Municipality municipality = new Municipality();
            municipality.setMunicipalityID(productDTO.getMunicipality().getMunicipalityID());

            // Set the Municipality entity in the Product
            product.setMunicipality(municipality);
        }

        productRepository.save(product);

        return new ResponseEntity<>("Product created successfully", HttpStatus.CREATED);
    }
    }

//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    @GetMapping("/list/{productId}")
//    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
//        ProductDTO product = productRepositoryJdbc.findById(productId);
//        return ResponseEntity.ok(product);
//    }

//    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
//    @PutMapping("/update/{productId}")
//    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO updatedProduct) {
//        // Perform validation or other checks as needed
//
//        // Set the product ID from the path variable into the updated product
//        updatedProduct.setProductID(productId);
//
//        // Update the product in the database
//        productRepositoryJdbc.update(updatedProduct);
//
//        // Fetch the updated product from the database
//        ProductDTO product = productRepositoryJdbc.findById(productId);
//
//        return ResponseEntity.ok(product);
//    }

    // Other CRUD operations or custom endpoints as needed


