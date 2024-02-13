package com.crm.wm.repository;

import com.crm.wm.dto.MunicipalityDTO;
import com.crm.wm.dto.ProductDTO;
import com.crm.wm.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryJdbc {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ProductDTO> findAll() {
        return jdbcTemplate.query(
                "SELECT p.productID, p.product_name, p.description, p.price, m.municipalityID, m.municipality_Name " +
                        "FROM Products p " +
                        "JOIN Municipalities m ON p.municipalityID = m.municipalityID",
                (rs, rowNum) ->
                        new ProductDTO(
                                rs.getLong("productID"),
                                rs.getString("product_name"),
                                rs.getString("description"),
                                rs.getDouble("price"),
                                new MunicipalityDTO(
                                        rs.getLong("municipalityID"),
                                        rs.getString("municipality_Name")
                                )
                        )
        );
    }

    public int insert(Product product) {
        return jdbcTemplate.update(
                "INSERT INTO Products (product_Name, description, price, municipalityID) VALUES (?, ?, ?, ?)",
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getMunicipality().getMunicipalityID()
        );
    }

    public ProductDTO findById(Long productId) {
        String query = "SELECT * FROM Products WHERE productID = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{productId}, productRowMapper());
    }

    public void update(ProductDTO updatedProduct) {
        String query = "UPDATE Products SET product_name = ?, description = ?, price = ? WHERE productID = ?";
        jdbcTemplate.update(query,
                updatedProduct.getProductName(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getProductID());
    }

    private RowMapper<ProductDTO> productRowMapper() {
        return (rs, rowNum) -> {
            ProductDTO product = new ProductDTO();
            product.setProductID(rs.getLong("productID"));
            product.setProductName(rs.getString("product_name"));
            product.setDescription(rs.getString("description"));
            product.setPrice(rs.getDouble("price"));
            // Set other fields as needed
            return product;
        };
    }
}
