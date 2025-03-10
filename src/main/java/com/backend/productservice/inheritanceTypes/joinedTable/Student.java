package com.backend.productservice.inheritanceTypes.joinedTable;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.StringTokenizer;

@Getter
@Setter
@Entity(name = "jt_student")
public class Student extends User {
    private String Batch;
}
