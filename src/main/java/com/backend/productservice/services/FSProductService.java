package com.backend.productservice.services;

import com.backend.productservice.dtos.FSProductDto;
import com.backend.productservice.exceptions.NoProductException;
import com.backend.productservice.models.Category;
import com.backend.productservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
@Service("FSProductService")
public class FSProductService implements ProductService{
    private RestTemplate restTemplate;


    public FSProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }
    @Override
    public List<Product> getAllProducts() {
        FSProductDto[] allDtoProducts = restTemplate.getForObject("https://fakestoreapi.com/products/",
                FSProductDto[].class);

        List<Product> allProducts = new ArrayList<>();
        for(FSProductDto i: allDtoProducts){
            allProducts.add(convertFSDtoProduct(i));
        }
        return allProducts;
//        throw new NullPointerException();
    }

    @Override

    public Product getSingleProduct(long id) throws NoProductException {

        //here we need to call the fake store to fetch the product -> we need to make http request.
        //the following will create one-to-one mapping.
        FSProductDto fsProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/"+id,
                FSProductDto.class);

        //! logic to convert the dto to the product.

        //EXCEPTION IF THE PRODUCT IS NULL. This will be thrown to the controller. Or we can put this in the try-catch block here.
        if(fsProductDto==null){
            throw new NoProductException("Enter a valid product id!", id);
        }
        return convertFSDtoProduct(fsProductDto);
    }

    private Product convertFSDtoProduct(FSProductDto fsProductDto){
        Product product = new Product();
        product.setId(fsProductDto.getId());
        product.setTitle(fsProductDto.getTitle());
        product.setPrice(fsProductDto.getPrice());

        Category category = new Category();
        category.setDescription(fsProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    public void deleteProduct(long id){
        //pass the id to the restTemplate and get the product.
//        return null;
    }

    @Override
    public Product updateProduct(long id, Product product) {
        /*
        1. request is the object we want to update(product).
        2. responseType is the .class thing(FSProductDto.class).
        3. logger is not necessary.
        4. Generic type is the dto we want to receive.
        5. In Spring, RequestCallback is an interface used by the RestTemplate to allow customization of the HTTP request before
           it is executed. Essentially, it lets you manipulate or set up the request—such as adding headers, writing to the request body,
           or performing other customizations—right before the request is sent over the network.
        6. .execute() will return a dto.
         */

        RequestCallback requestCallback = restTemplate.httpEntityCallback(product, FSProductDto.class);
        HttpMessageConverterExtractor<FSProductDto> responseExtractor = new HttpMessageConverterExtractor(FSProductDto.class, restTemplate.getMessageConverters());
        FSProductDto temp = (FSProductDto)restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.PATCH, requestCallback, responseExtractor);
        return convertFSDtoProduct(temp);
    }

    @Override
    public Product replaceProduct(long id, Product product) {
//        restTemplate.put();
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }
}
