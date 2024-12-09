package com.be.ecommerce.dao.imple;

import com.be.ecommerce.dao.interf.VariantPropertyDao;
import com.be.ecommerce.dto.database.VariantPropertyDbResponse;
import com.be.ecommerce.model.VariantProperty;
import com.be.ecommerce.sql.VariantPropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class VariantPropertyDaoImpl implements VariantPropertyDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public VariantPropertyDbResponse getAllVariantProperties(int productId){
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()){
            // call
            CallableStatement stmt = conn.prepareCall(VariantPropertyUtils.GET_ALL_PROPERTIES.getCall());

            // execute
            stmt.setInt(1, productId);

            // retrieve data
            ResultSet rs = stmt.executeQuery();
            List<VariantProperty> list = new ArrayList<>();
            while (rs.next()){
                int propertyId = rs.getInt(1);
                int prodId = rs.getInt(2);
                String propertyName = rs.getString(3);
                VariantProperty prop = new VariantProperty(propertyId, prodId, propertyName);
                list.add(prop);
            }

            return VariantPropertyDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Return all properties of product").
                    variantPropertyList(list)
                    .build();

        } catch (SQLException e){
            return VariantPropertyDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public VariantPropertyDbResponse addVariantProperty(VariantProperty variantProperty) {
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()){
            // call
            CallableStatement stmt = conn.prepareCall(VariantPropertyUtils.ADD_PROPERTY.getCall());

            // execute
            stmt.setInt(1, variantProperty.getProductId());
            stmt.setString(2, variantProperty.getPropertyName());

            // retrieve data
            stmt.execute();

            return VariantPropertyDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Property has been added successfully")
                    .build();

        } catch (SQLException e){
            return VariantPropertyDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public VariantPropertyDbResponse updateVariantProperty(VariantProperty variantProperty) {
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()){
            // call
            CallableStatement stmt = conn.prepareCall(VariantPropertyUtils.UPDATE_PROPERTY.getCall());

            // execute
            stmt.setInt(1, variantProperty.getProductId());
            stmt.setString(2, variantProperty.getPropertyName());

            // retrieve data
            stmt.execute();

            return VariantPropertyDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Property has been updated successfully")
                    .build();

        } catch (SQLException e){
            return VariantPropertyDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }

    @Override
    public VariantPropertyDbResponse deleteVariantProperty(int productId) {
        try (Connection conn = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection()){
            // call
            CallableStatement stmt = conn.prepareCall(VariantPropertyUtils.DELETE_PROPERTY.getCall());

            // execute
            stmt.setInt(1, productId);

            // retrieve data
            stmt.execute();

            return VariantPropertyDbResponse.builder().
                    errorCode(0).
                    sqlState("OK").
                    message("Property has been deleted successfully")
                    .build();

        } catch (SQLException e){
            return VariantPropertyDbResponse.builder().
                    errorCode(e.getErrorCode()).
                    sqlState(e.getSQLState()).
                    message(e.getMessage())
                    .build();
        }
    }
}
