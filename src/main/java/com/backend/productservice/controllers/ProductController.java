package com.backend.productservice.controllers;

import com.backend.productservice.exceptions.NoProductException;
import com.backend.productservice.models.Product;
import com.backend.productservice.services.FSProductService;
import com.backend.productservice.services.ProductService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

@Getter
@Setter
@RestController
@RequestMapping("/products")
public class ProductController {

//    @Autowired
    private ProductService productService;

    //autowired can also be used here.
    //if the following is commented out, then we will get null, and then we will have to handle the null pointer exception.
    public ProductController(@Qualifier("DBProductService") ProductService productService){
        this.productService = productService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) throws NoProductException {


//        ResponseEntity<Product> responseEntity;
//        HttpStatusCode code = null;
//        Product productFromService = null;
//        try{
//            productFromService = productService.getSingleProduct(id);
//            code = HttpStatus.OK;
//        } catch (Exception e){
//            code = HttpStatus.BAD_REQUEST;
//        }
//        responseEntity = new ResponseEntity<>(
//                productFromService,
//                code  //DOESN'T DO ANYTHING. IN OUR CONTROL HERE.
//        );

        //if the product is null then we will get the error that we used in the service if we don't handle it here.
        //because we want the controller to be as dumb as possible, we will use the controller advice to handle it.
        ResponseEntity<Product> responseEntity = new ResponseEntity<>(
                productService.getSingleProduct(id),
                HttpStatus.OK
        );

        return responseEntity;
    }

    @GetMapping
    public Page<Product> getAllProducts(@RequestParam("pageNumber") int pageNumber, @RequestParam("pageSize") int pageSize){
        return productService.getAllProducts(pageNumber, pageSize);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") long id){

        productService.deleteProduct(id);
    }

    @PatchMapping("/{id}")
    public Product updateProduct(@PathVariable("id") long id, @RequestBody Product product) throws NoProductException {
        //update the product to the product passed.
        return productService.updateProduct(id, product);
    }

    @PutMapping("/{id}")
    public Product replaceProduct(@PathVariable("id") long id, Product product){
        //replace the product with the product passed.
        return null;
    }

    @PostMapping
    public Product addNewProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }
}
