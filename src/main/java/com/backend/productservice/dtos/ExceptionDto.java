package com.backend.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.*;

@Getter
@Setter
public class ExceptionDto {
    String message;
    String resolution;
}
