package com.backend.productservice.services;

import com.backend.productservice.exceptions.NoProductException;
import com.backend.productservice.models.Category;
import com.backend.productservice.models.Product;
import com.backend.productservice.repositories.CategoryRepository;
import com.backend.productservice.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service("DBProductService")
//@Primary
public class DBProductService implements ProductService {
    //repo for this service
    private final ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    public DBProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    //! READ
    @Override
    public Product getSingleProduct(long id) throws NoProductException {
        //make a call to the db to get the product with that id.
        //but service should talk to db through repository.
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new NoProductException("Product with id "+id+" doesn't exist", id);
        }
        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


    //!DELETE
    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
//        return null;
    }


    //! UPDATE
    @Override
    public Product updateProduct(long id, Product product) throws NoProductException{
        Optional<Product> toBeUpdatedOpt = productRepository.findById(id);
        if(toBeUpdatedOpt.isEmpty()){
            throw new NoProductException("Product with id: "+id+" is not present.", id);
        }
        Product toBeUpdated = toBeUpdatedOpt.get(); //we are sure it's not empty.
        //title, price, category.
        if(product.getTitle()!=null){
            toBeUpdated.setTitle(product.getTitle());
        }
        if(product.getPrice()!=0){
            toBeUpdated.setPrice(product.getPrice());
        }

        return productRepository.save(toBeUpdated);
    }

    @Override
    public Product replaceProduct(long id, Product product) {
        return null;
    }


    //! CREATE
    @Override
    public Product addProduct(Product product) {
        //the category could also be null, so we need to check that and if the id is null
        //then we need to create a new category and then pass it to the product.
        Category category = product.getCategory();
        if(category == null){
            //create a new category and save it to the database.
            category = categoryRepository.save(product.getCategory());
            //the other attributes can be set here.
            product.setCategory(category);
        }
        return productRepository.save(product);
    }
}
