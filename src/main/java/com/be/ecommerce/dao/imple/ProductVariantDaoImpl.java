package com.be.ecommerce.dao.imple;

import com.be.ecommerce.dao.interf.ProductVariantDao;
import com.be.ecommerce.dto.database.ProductVariantDbResponse;
import com.be.ecommerce.model.ProductVariant;
import com.be.ecommerce.sql.ProductVariantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductVariantDaoImpl implements ProductVariantDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ProductVariantDbResponse addProductVariant(ProductVariant productVariant){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            // call
            CallableStatement stmt = conn.prepareCall(ProductVariantUtils.ADD_VARIANT.getCall());

            // pass parameters
            stmt.setInt(1, productVariant.getProductId());
            stmt.setString(2, productVariant.getName());
            stmt.setBigDecimal(3, productVariant.getPrice());
            stmt.setInt(4, productVariant.getQuantity());
            stmt.setString(5, productVariant.getImageUrl());

            // execute
            stmt.execute();

            return ProductVariantDbResponse.builder().
                                            errorCode(0).
                                            sqlState("OK").
                                            message("Product variant has been added successfully")
                                            .build();

        } catch (SQLException e){
            return ProductVariantDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductVariantDbResponse updateProductVariant(ProductVariant productVariant){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            // call
            CallableStatement stmt = conn.prepareCall(ProductVariantUtils.UPDATE_VARIANT.getCall());

            // pass parameters
            stmt.setBigDecimal(1, productVariant.getPrice());
            stmt.setInt(2, productVariant.getQuantity());
            stmt.setString(3, productVariant.getImageUrl());

            // execute
            stmt.execute();

            return ProductVariantDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Product variant has been updated successfully")
                    .build();

        } catch (SQLException e){
            return ProductVariantDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductVariantDbResponse deleteProductVariant(int productId){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            // call
            CallableStatement stmt = conn.prepareCall(ProductVariantUtils.DELETE_VARIANT.getCall());

            // pass parameters
            stmt.setInt(1, productId);

            // execute
            stmt.execute();

            return ProductVariantDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Product variant has been deleted successfully")
                    .build();

        } catch (SQLException e){
            return ProductVariantDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public ProductVariantDbResponse getAllProductVariants(int productId){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()) {
            // call
            CallableStatement stmt = conn.prepareCall(ProductVariantUtils.GET_ALL_VARIANTS.getCall());

            // pass parameters
            stmt.setInt(0, productId);

            // execute
            ResultSet rs = stmt.executeQuery();

            // retrieve data
            List<ProductVariant> list = new ArrayList<>();
            while (rs.next()){
                int variantId = rs.getInt(1);
                int prodId = rs.getInt(2);
                String name = rs.getString(3);
                BigDecimal price = rs.getBigDecimal(4);
                int quantity = rs.getInt(5);
                String imageUrl = rs.getString(6);

                ProductVariant variant = new ProductVariant(variantId, prodId, name, price, quantity, imageUrl);
                list.add(variant);
            }


            return ProductVariantDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Return all variants of product").
                    productVariants(list)
                    .build();

        } catch (SQLException e){
            return ProductVariantDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }
}
