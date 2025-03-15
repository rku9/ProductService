package com.backend.productservice.services;

import com.backend.productservice.exceptions.NoProductException;
import com.backend.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ProductService {
    Product getSingleProduct(long id) throws NoProductException;
    Page<Product> getAllProducts(int pageNumber, int pageSize);
    void deleteProduct(long id);

    Product updateProduct(long id, Product product) throws NoProductException;

    Product replaceProduct(long id, Product product);
    Product addProduct(Product product);
}
