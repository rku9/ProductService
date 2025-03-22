package com.backend.productservice.controllers;

import com.backend.productservice.exceptions.NoProductException;
import com.backend.productservice.models.Product;
import com.backend.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {
    @Autowired //real productController will be called.
    private ProductController productController;
    @MockitoBean(name = "FSProductService")
    private ProductService productService;
    @Test
    void tesGetProductByIdPositive() throws NoProductException {
        /*
        by doing the following, this controller will call the service, and we don't want that
        as that won't be a test in isolation as we are testing the controller here and not the service.
        We need to tell the test to use something else(hard coded value) if productService is called.
        For that we need the actual productService. But we will not autowire it as it will use the
        actual productService. We will use @MockBean.
        Here when the call goes to the controller where we are assigning the product to the one we
        get from the service call, it won't make the call to the service but instead, assign the product to
        the expectedProduct and then return it.
        If we don't return this product but instead return a new Product(), the test will fail.
         */
        Product expectedProduct = new Product();

        when(productService.getSingleProduct(1L)).thenReturn(expectedProduct);
        Product actualProduct = productController.getSingleProduct(1L).getBody();
        assertEquals(expectedProduct, actualProduct); //checks the reference in absence of class-specific equals method.
    }

    @Test
    void tesGetProductByIdNegative() throws NoProductException {
        when(productService.getSingleProduct(-2L)).thenThrow(NoProductException.class);
        assertThrows(NoProductException.class, () -> {
            productController.getSingleProduct(2L); //null product will be returned and this will fail the test.
        });
    }

    @Test
    void testGetProductById() {
    }

    @Test
    void getAllProducts() {
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void updateProduct() {
    }

    @Test
    void replaceProduct() {
    }

    @Test
    void addNewProduct() {
    }

    @Test
    void getProductService() {
    }

    @Test
    void setProductService() {
    }
}