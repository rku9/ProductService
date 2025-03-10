package com.backend.productservice.inheritanceTypes.tablePerClass;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "table_teacher")
public class Teacher extends User {
    private String subject;
}
