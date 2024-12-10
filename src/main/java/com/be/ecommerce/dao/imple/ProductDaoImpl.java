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
            stmt.setInt(2, product.getQuantity());
            stmt.setBigDecimal(3, product.getProductPrice());
            stmt.setInt(4, product.getSellerId());
            stmt.setInt(5, product.getCategoryId());
            stmt.setString(6, product.getProductDescription());

            // execute
            stmt.execute();

            return ProductDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message(null)
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
            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setInt(3, product.getQuantity());
            stmt.setBigDecimal(4, product.getProductPrice());
            stmt.setString(5, product.getProductDescription());

            // execute
            stmt.execute();

            return ProductDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message(null)
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
                    message(null)
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
    public ProductDbResponse getAllProducts(int sellerId){
        try (Connection conn = Objects.requireNonNull(Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection())){
            // call procedure
            CallableStatement stmt = conn.prepareCall(ProductUtils.GET_ALL_PRODUCTS.getCall());

            // pass param
            stmt.setInt(1, sellerId);

            // execute
            ResultSet rs = stmt.executeQuery();

            // retrieve data
            List<Product> list = new ArrayList<>();
            while (rs.next()){
                int id = rs.getInt(1);
                String name = rs.getString(2);
                BigDecimal price = rs.getBigDecimal(3);
                int quantity = rs.getInt(4);
                String shopName = rs.getString(5);
                String description = rs.getString(6);
                String url = rs.getString(7);
                Product product = new Product(id, name, quantity, price, sellerId, shopName, description, 0, null);
                list.add(product);
            }

            return ProductDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message(null).
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
