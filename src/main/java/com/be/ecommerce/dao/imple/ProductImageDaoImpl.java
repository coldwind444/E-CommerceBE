package com.be.ecommerce.dao.imple;

import com.be.ecommerce.dao.interf.ProductImageDao;
import com.be.ecommerce.dto.database.ProductImageDbResponse;
import com.be.ecommerce.model.ProductImage;
import com.be.ecommerce.sql.ProductImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Repository
public class ProductImageDaoImpl implements ProductImageDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ProductImageDbResponse getProductImage(int productId){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()){
            // call
            CallableStatement stmt = conn.prepareCall(ProductImageUtils.GET_IMAGE.getCall());

            // pass parameters
            stmt.setInt(1, productId);

            // execute
            ResultSet rs = stmt.executeQuery();
            int prodId = rs.getInt(1);
            int imgId = rs.getInt(2);
            String url = rs.getString(3);
            ProductImage productImage = new ProductImage(prodId, imgId, url);

            return ProductImageDbResponse.builder().
                                        errorCode(0).sqlState("OK").
                                        message("Product image has been found").
                                        productImage(productImage)
                                        .build();
        } catch (SQLException e){
            return ProductImageDbResponse.builder().
                    errorCode(e.getErrorCode()).sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductImageDbResponse addProductImage(ProductImage productImage){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()){
            // call
            CallableStatement stmt = conn.prepareCall(ProductImageUtils.ADD_IMAGE.getCall());

            // pass parameters
            stmt.setInt(1, productImage.getProductId());
            stmt.setString(2, productImage.getImageUrl());

            // execute
            stmt.execute();

            return ProductImageDbResponse.builder().
                    errorCode(0).sqlState("OK").
                    message("Product image has been added")
                    .build();

        } catch (SQLException e){
            return ProductImageDbResponse.builder().
                    errorCode(e.getErrorCode()).sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductImageDbResponse updateProductImage(ProductImage productImage){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()){
            // call
            CallableStatement stmt = conn.prepareCall(ProductImageUtils.UPDATE_IMAGE.getCall());

            // pass parameters
            stmt.setInt(1, productImage.getProductId());
            stmt.setString(2, productImage.getImageUrl());

            // execute
            stmt.execute();

            return ProductImageDbResponse.builder().
                    errorCode(0).sqlState("OK").
                    message("Product image has been added").
                    productImage(productImage)
                    .build();

        } catch (SQLException e){
            return ProductImageDbResponse.builder().
                    errorCode(e.getErrorCode()).sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductImageDbResponse deleteProductImage(int productId){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()){
            // call
            CallableStatement stmt = conn.prepareCall(ProductImageUtils.DELETE_IMAGE.getCall());

            // pass parameters
            stmt.setInt(1, productId);

            // execute
            stmt.execute();

            return ProductImageDbResponse.builder().
                    errorCode(0).sqlState("OK").
                    message("Product image has been added")
                    .build();

        } catch (SQLException e){
            return ProductImageDbResponse.builder().
                    errorCode(e.getErrorCode()).sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

}
