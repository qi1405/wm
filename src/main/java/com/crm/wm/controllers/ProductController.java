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
import java.util.Optional;
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
    @GetMapping("/{productID}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productID) {
        Optional<Product> productOptional = productRepository.findById(productID);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDTO productDTO = new ProductDTO(product);
            return ResponseEntity.ok(productDTO);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
    @PutMapping("/update/{productID}")
    public ResponseEntity<?> updateProduct(@RequestParam Long productID, @RequestBody ProductDTO productDTO) {
        try {
            Product existingProduct = productRepository.findById(productID).orElse(null);
            if (existingProduct != null) {
                // Update existing product with new data
                existingProduct.setProductName(productDTO.getProductName());
                existingProduct.setDescription(productDTO.getDescription());
                existingProduct.setPrice(productDTO.getPrice());

                // Update municipality if provided
                if (productDTO.getMunicipality() != null) {
                    // Update municipalityID only as an example
                    existingProduct.getMunicipality().setMunicipalityID(productDTO.getMunicipality().getMunicipalityID());
                }

                productRepository.updateProduct(existingProduct); // Call the default method from repository
                return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error updating product: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}