package com.be.ecommerce.service;

import com.be.ecommerce.dao.imple.ProductDaoImpl;
import com.be.ecommerce.dto.app.ProductResponse;
import com.be.ecommerce.dto.database.ProductDbResponse;
import com.be.ecommerce.model.Product;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private ProductDaoImpl productDao;

    @Autowired
    private Cloudinary cloudinary;

    public String uploadImageToCloudinary(String localPath) {
        try {

            File file = new File(localPath);

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
        ProductDbResponse productDbResponse = productDao.addProduct(product);
        int status = (productDbResponse.getErrorCode() == 0) ? 200 : 500;

        String msg = status == 200 ? "Process exit without any exceptions :)" :
                                     "Process exit with errors :(" ;

        return ProductResponse.builder().
                status(status).
                message(msg).
                errorCode(productDbResponse.getErrorCode())
                .errorMessage(productDbResponse.getMessage())
                .build();
    }

    public ProductResponse updateProduct(Product product) {
        ProductDbResponse productDbResponse = productDao.updateProduct(product);
        int status = (productDbResponse.getErrorCode() == 0) ? 200 : 500;

        String msg = status == 200 ? "Process exit without any exceptions :)" :
                "Process exit with errors :(" ;

        return ProductResponse.builder().
                status(status).
                message(msg).
                errorCode(productDbResponse.getErrorCode())
                .errorMessage(productDbResponse.getMessage())
                .build();
    }

    public ProductResponse deleteProduct(int id) {
        ProductDbResponse productDbResponse = productDao.deleteProduct(id);
        int status = (productDbResponse.getErrorCode() == 0) ? 200 : 500;

        String msg = status == 200 ? "Process exit without any exceptions :)" :
                "Process exit with errors :(" ;

        return ProductResponse.builder().
                status(status).
                message(msg).
                errorCode(productDbResponse.getErrorCode())
                .errorMessage(productDbResponse.getMessage())
                .build();
    }

    public ProductResponse getAllProducts(int sellerId) {
        ProductDbResponse productDbResponse = productDao.getAllProducts(sellerId);
        int status = (productDbResponse.getErrorCode() == 0) ? 200 : 500;

        String msg = status == 200 ? "Process exit without any exceptions :)" :
                                     "Process exit with errors :(" ;

        return ProductResponse.builder().
                status(status).
                message(msg).
                errorCode(productDbResponse.getErrorCode())
                .errorMessage(productDbResponse.getMessage())
                .productList(productDbResponse.getProductList())
                .build();

    }
}
