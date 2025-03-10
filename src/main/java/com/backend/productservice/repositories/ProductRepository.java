package com.backend.productservice.repositories;

import com.backend.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //here we will need all the CRUD operations.
    //but we won't write the queries and instead use JPA repository.

    //! we don't need to write the methods here. JPA will take care of it.
//    @Override
//    Optional<Product> findById(Long id);
}
