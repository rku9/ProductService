package com.backend.productservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Getter
@Setter
@Entity
public class Category extends BaseModel{

    private String name;
    private String description;
    @OneToMany(fetch = jakarta.persistence.FetchType.EAGER, mappedBy = "category")
    //ManyToOne won't work here due to List<Products>.
    @JsonBackReference //THE PRODUCTS WILL NOT BE SHOWN TO STOP RECURSION
    private List<Product> products; //lazy
}
