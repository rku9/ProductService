package com.backend.productservice.exceptions;

import java.io.*;
import java.util.*;

public class NoProductException extends Exception{
    private long id;
    public NoProductException(String message, long id) {
        super(message);
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
