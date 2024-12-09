package com.be.ecommerce.service;

import com.be.ecommerce.dao.imple.ProductDaoImpl;
import com.be.ecommerce.dao.imple.ProductImageDaoImpl;
import com.be.ecommerce.dao.imple.ProductVariantDaoImpl;
import com.be.ecommerce.dao.imple.VariantPropertyDaoImpl;
import com.be.ecommerce.dto.app.ErrorLog;
import com.be.ecommerce.dto.app.ProductResponse;
import com.be.ecommerce.dto.database.ProductDbResponse;
import com.be.ecommerce.dto.database.ProductImageDbResponse;
import com.be.ecommerce.dto.database.ProductVariantDbResponse;
import com.be.ecommerce.dto.database.VariantPropertyDbResponse;
import com.be.ecommerce.model.Product;
import com.be.ecommerce.model.ProductImage;
import com.be.ecommerce.model.ProductVariant;
import com.be.ecommerce.model.VariantProperty;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private ProductDaoImpl productDao;

    @Autowired
    private ProductImageDaoImpl productImageDao;

    @Autowired
    private ProductVariantDaoImpl productVariantDao;

    @Autowired
    private VariantPropertyDaoImpl variantPropertyDao;

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImageToCloudinary(String localpath) {
        try {
            File file = new File(localpath);

            if (file.exists() && file.isFile()) {
                // Upload the image file to Cloudinary and get the result as a map
                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

                // Extract the URL of the uploaded image

                // Return the image URL
                return (String) uploadResult.get("url");
            } else {
                throw new IllegalArgumentException("File does not exist or is not a valid file.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image to Cloudinary", e);
        }
    }

    public ProductResponse addProduct(Product product) {
        List<ErrorLog> logs = new ArrayList<>();
        boolean addVariantSuccess = true;
        boolean addProductImageSuccess = true;
        boolean addVariantPropSuccess = true;

        // phase 1: add product
        ProductDbResponse productDbResponse = productDao.addProduct(product);
        if (productDbResponse.getErrorCode() != 0){
            int errorCode = productDbResponse.getErrorCode();
            String msg = productDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
        }

        // phase 2: add image
        for (ProductImage img : product.getProductImageList()){
            img.setImageUrl(uploadImageToCloudinary(img.getImageUrl()));
            ProductImageDbResponse productImageDbResponse = productImageDao.addProductImage(img);
            if (productImageDbResponse.getErrorCode() != 0) {
                int errorCode = productImageDbResponse.getErrorCode();
                String msg = productImageDbResponse.getMessage();
                logs.add(new ErrorLog(errorCode, msg));
                addProductImageSuccess = false;
            }
        }

        // phase 3: add property
        for (VariantProperty prop : product.getVariantPropertyList()) {
            VariantPropertyDbResponse variantPropertyDbResponse = variantPropertyDao.addVariantProperty(prop);
            if (variantPropertyDbResponse.getErrorCode() != 0) {
                int errorCode = variantPropertyDbResponse.getErrorCode();
                String msg = variantPropertyDbResponse.getMessage();
                logs.add(new ErrorLog(errorCode, msg));
                addVariantPropSuccess = false;
            }
        }

        // phase 4: add variant
        for (ProductVariant variant : product.getProductVariantList()) {
            variant.setImageUrl(uploadImageToCloudinary(variant.getImageUrl()));
            ProductVariantDbResponse productVariantDbResponse = productVariantDao.addProductVariant(variant);
            if (productVariantDbResponse.getErrorCode() != 0) {
                int errorCode = productVariantDbResponse.getErrorCode();
                String msg = productVariantDbResponse.getMessage();
                logs.add(new ErrorLog(errorCode, msg));
                addVariantSuccess = false;
            }
        }

        // phase 5: manage response general info
        int status = (productDbResponse.getErrorCode() == 0
                    && addProductImageSuccess && addVariantSuccess && addVariantPropSuccess)? 200 : 500;

        String msg = status == 200 ? "Process exit without any exceptions :)" :
                                     "Process exit with errors :(" ;

        return ProductResponse.builder().
                status(status).
                message(msg).
                errorLogList(logs)
                .build();
    }

    public ProductResponse updateProduct(Product product) {
        List<ErrorLog> logs = new ArrayList<>();
        boolean updateVariantSuccess = true;
        boolean updateProductImageSuccess = true;
        boolean updateVariantPropSuccess = true;

        // phase 1: update variant
        for (ProductVariant variant : product.getProductVariantList()) {
            variant.setImageUrl(uploadImageToCloudinary(variant.getImageUrl()));
            ProductVariantDbResponse productVariantDbResponse = productVariantDao.updateProductVariant(variant);
            if (productVariantDbResponse.getErrorCode() != 0) {
                int errorCode = productVariantDbResponse.getErrorCode();
                String msg = productVariantDbResponse.getMessage();
                logs.add(new ErrorLog(errorCode, msg));
                updateVariantSuccess = false;
            }
        }

        // phase 2: update property
        for (VariantProperty prop : product.getVariantPropertyList()) {
            VariantPropertyDbResponse variantPropertyDbResponse = variantPropertyDao.updateVariantProperty(prop);
            if (variantPropertyDbResponse.getErrorCode() != 0) {
                int errorCode = variantPropertyDbResponse.getErrorCode();
                String msg = variantPropertyDbResponse.getMessage();
                logs.add(new ErrorLog(errorCode, msg));
                updateVariantPropSuccess = false;
            }
        }

        // phase 3: update image
        for (ProductImage img : product.getProductImageList()){
            img.setImageUrl(uploadImageToCloudinary(img.getImageUrl()));
            ProductImageDbResponse productImageDbResponse = productImageDao.updateProductImage(img);
            if (productImageDbResponse.getErrorCode() != 0) {
                int errorCode = productImageDbResponse.getErrorCode();
                String msg = productImageDbResponse.getMessage();
                logs.add(new ErrorLog(errorCode, msg));
                updateProductImageSuccess = false;
            }
        }

        // phase 4: update product
        ProductDbResponse productDbResponse = productDao.updateProduct(product);
        if (productDbResponse.getErrorCode() != 0){
            int errorCode = productDbResponse.getErrorCode();
            String msg = productDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
        }

        // phase 5: manage response general info
        int status = (productDbResponse.getErrorCode() == 0
                && updateProductImageSuccess && updateVariantSuccess && updateVariantPropSuccess)? 200 : 500;

        String msg = status == 200 ? "Process exit without any exceptions :)" :
                                     "Process exit with errors :(" ;


        return ProductResponse.builder().
                status(status).
                message(msg).
                errorLogList(logs)
                .build();
    }

    public ProductResponse deleteProduct(int id) {
        List<ErrorLog> logs = new ArrayList<>();
        boolean deleteVariantSuccess = true;
        boolean deleteProductImageSuccess = true;
        boolean deleteVariantPropSuccess = true;

        // phase 1: delete variant
        ProductVariantDbResponse productVariantDbResponse = productVariantDao.deleteProductVariant(id);
        if (productVariantDbResponse.getErrorCode() != 0) {
            int errorCode = productVariantDbResponse.getErrorCode();
            String msg = productVariantDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
            deleteVariantSuccess = false;
        }

        // phase 2: delete property
        VariantPropertyDbResponse variantPropertyDbResponse = variantPropertyDao.deleteVariantProperty(id);
        if (variantPropertyDbResponse.getErrorCode() != 0) {
            int errorCode = variantPropertyDbResponse.getErrorCode();
            String msg = variantPropertyDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
            deleteVariantPropSuccess = false;
        }

        // phase 3: delete image
        ProductImageDbResponse productImageDbResponse = productImageDao.deleteProductImage(id);
        if (productImageDbResponse.getErrorCode() != 0) {
            int errorCode = productImageDbResponse.getErrorCode();
            String msg = productImageDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
            deleteProductImageSuccess = false;
        }

        // phase 4: delete product
        ProductDbResponse productDbResponse = productDao.deleteProduct(id);
        if (productDbResponse.getErrorCode() != 0){
            int errorCode = productDbResponse.getErrorCode();
            String msg = productDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
        }

        // phase 5: manage response general info
        int status = (productDbResponse.getErrorCode() == 0
                && deleteProductImageSuccess && deleteVariantSuccess && deleteVariantPropSuccess)? 200 : 500;

        String msg = status == 200 ? "Process exit without any exceptions :)" :
                                     "Process exit with errors :(" ;

        return ProductResponse.builder().
                status(status).
                message(msg).
                errorLogList(logs)
                .build();
    }

    public ProductResponse getProduct(int id) {
        List<ErrorLog> logs = new ArrayList<>();
        boolean addVariantSuccess = true;
        boolean addProductImageSuccess = true;
        boolean addVariantPropSuccess = true;

        // phase 1: get product
        ProductDbResponse productDbResponse = productDao.getProduct(id);
        if (productDbResponse.getErrorCode() != 0){
            int errorCode = productDbResponse.getErrorCode();
            String msg = productDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
        }

        // phase 2: get image
        ProductImageDbResponse productImageDbResponse = productImageDao.getProductImage(id);
        if (productImageDbResponse.getErrorCode() != 0) {
            int errorCode = productImageDbResponse.getErrorCode();
            String msg = productImageDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
            addProductImageSuccess = false;
        }

        // phase 3: get property
        VariantPropertyDbResponse variantPropertyDbResponse = variantPropertyDao.getAllVariantProperties(id);
        if (variantPropertyDbResponse.getErrorCode() != 0) {
            int errorCode = variantPropertyDbResponse.getErrorCode();
            String msg = variantPropertyDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
            addVariantPropSuccess = false;
        }

        // phase 4: get variant
        ProductVariantDbResponse productVariantDbResponse = productVariantDao.getAllProductVariants(id);
        if (productVariantDbResponse.getErrorCode() != 0) {
            int errorCode = productVariantDbResponse.getErrorCode();
            String msg = productVariantDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
            addVariantSuccess = false;
        }

        // phase 5: manage response general info
        int status = (productDbResponse.getErrorCode() == 0
                && addProductImageSuccess && addVariantSuccess && addVariantPropSuccess)? 200 : 500;

        String msg = status == 200 ? "Process exit without any exceptions :)" :
                                     "Process exit with errors :(" ;

        return ProductResponse.builder().
                status(status).
                message(msg).
                errorLogList(logs)
                .build();
    }

    public ProductResponse getAllProducts() {
        List<ErrorLog> logs = new ArrayList<>();

        ProductDbResponse productDbResponse = productDao.getAllProducts();

        if (productDbResponse.getErrorCode() != 0){
            int errorCode = productDbResponse.getErrorCode();
            String msg = productDbResponse.getMessage();
            logs.add(new ErrorLog(errorCode, msg));
        }

        int status = (productDbResponse.getErrorCode() == 0) ? 200 : 500;
        String msg = status == 200 ? "Process exit without any exceptions :)" :
                                     "Process exit with errors :(" ;

        return ProductResponse.builder().
                status(status).
                message(msg).
                errorLogList(logs).
                productList(productDbResponse.getProductList())
                .build();

    }
}
