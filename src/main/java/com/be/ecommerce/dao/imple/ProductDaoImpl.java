package com.be.ecommerce.dao.imple;

import com.be.ecommerce.dao.interf.ProductDao;
import com.be.ecommerce.dto.database.ProductDbResponse;
import com.be.ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.be.ecommerce.sql.ProductUtils;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ProductDbResponse addProduct(Product product){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            // call procedure
            CallableStatement stmt = conn.prepareCall(ProductUtils.ADD_PRODUCT.getCall());

            // pass parameters
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductDescription());
            stmt.setLong(3, product.getQuantity());
            stmt.setInt(4, product.getSellerId());
            stmt.setBigDecimal(5, product.getProductPrice());

            // execute
            stmt.execute();

            return ProductDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Product has been added successfully")
                    .build();

        } catch (SQLException e){
            return ProductDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductDbResponse updateProduct(Product product){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            // call procedure
            CallableStatement stmt = conn.prepareCall(ProductUtils.UPDATE_PRODUCT.getCall());

            // pass parameters
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getProductDescription());
            stmt.setLong(3, product.getQuantity());
            stmt.setBigDecimal(4, product.getProductPrice());

            // execute
            stmt.execute();

            return ProductDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Product has been updated successfully")
                    .build();

        } catch (SQLException e){
            return ProductDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductDbResponse deleteProduct(int productId){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            // call procedure
            CallableStatement stmt = conn.prepareCall(ProductUtils.DELETE_PRODUCT.getCall());

            // pass parameters
            stmt.setInt(1, productId);

            // execute
            stmt.execute();

            return ProductDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Product has been deleted successfully")
                    .build();

        } catch (SQLException e){
            return ProductDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductDbResponse getProduct(int id) {
        try (Connection conn = Objects.requireNonNull(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection())){
            // call procedure
            CallableStatement stmt = conn.prepareCall(ProductUtils.GET_PRODUCT_BY_ID.getCall());

            // pass parameter
            stmt.setInt(1, id);

            // execute
            ResultSet rs = stmt.executeQuery();

            // retrieve data
            int productId = rs.getInt(1);
            String productName = rs.getString(2);
            String productDescription = rs.getString(3);
            int quantity = rs.getInt(4);
            BigDecimal price = rs.getBigDecimal(5);
            int sellerUserId = rs.getInt(6);

            // map data to model
            Product product = Product.builder().
                                productId(productId).
                                productName(productName).
                                productDescription(productDescription).
                                quantity(quantity).
                                productPrice(price).
                                sellerId(sellerUserId)
                                .build();

            return ProductDbResponse.builder().
                                    errorCode(0).
                                    sqlState("OK").
                                    message("Product has been found").
                                    product(product)
                                    .build();
        } catch (SQLException e){
            return ProductDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductDbResponse getAllProducts(){
        try (Connection conn = Objects.requireNonNull(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection())){
            // call procedure
            CallableStatement stmt = conn.prepareCall(ProductUtils.GET_ALL_PRODUCTS.getCall());

            // execute
            ResultSet rs = stmt.executeQuery();

            // retrieve data
            List<Product> list = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String description = rs.getString(3);
                int quantity = rs.getInt(4);
                BigDecimal price = rs.getBigDecimal(5);
                int sellerId = rs.getInt(6);
                Product product = new Product(id, name, quantity, price, sellerId, description, null, null, null);
                list.add(product);
            }

            return ProductDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Return all products").
                    productList(list)
                    .build();

        } catch (SQLException e){
            return ProductDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }
}
