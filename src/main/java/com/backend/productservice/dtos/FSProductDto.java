package com.backend.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;

@Getter
@Setter
public class FSProductDto {
    private long id;
    private String title;
    private double price;
    private String category;
    private String description;
    private String image;

}
