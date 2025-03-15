package com.backend.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;

@Getter
@Setter
//this mirrors the data type and the attributes of the API.
public class FSProductDto {
    private long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

}
