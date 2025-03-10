package com.backend.productservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;

@Getter
@Setter
@Entity
public class  Product extends BaseModel{
    private String title;
    private double price;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn
//            (cascade = CascadeType.ALL) -> not recommended in many-to-one?
//    @JsonBackReference  //! CATEGORY WILL BE HIDDEN TO STEP RECURSION
    @JsonManagedReference
    private Category category;
}
