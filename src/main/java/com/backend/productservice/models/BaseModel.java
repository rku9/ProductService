package com.backend.productservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;
@Getter
@Setter
@MappedSuperclass
public class BaseModel implements Serializable{
    @Id //primary key
    @GeneratedValue(strategy =  GenerationType.IDENTITY) //auto increment
    private long id;
    private Date createdAt;
    private Date updatedAt;
}