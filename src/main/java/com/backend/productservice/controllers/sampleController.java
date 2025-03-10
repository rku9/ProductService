package com.backend.productservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

@RestController //coming from spring web. should be able to write http apis.
@RequestMapping("/say") //after reaching the server if we have /say we will reach this class.
public class sampleController {

    //getMapping to get the data.
    @GetMapping("/hello/{name1}/{name2}")   //name is brackets is the variable(path variable) and not the end point.
    public String sayHello(@PathVariable("name1") String xyz, @PathVariable("name2") char abc){
        //the name after the pathVariable is to map the variable xyz to the name in the api path variable.
        return "Hello "+xyz+"!!!"+" "+(int)abc;
    }

    @GetMapping("/hello/{name1}")
    public String sayHello2(@PathVariable("name1") String xyz){
        //the name after the pathVariable is to map the variable xyz to the name in the api path variable.
//        System.out.println("hello "+xyz+System.lineSeparator());
//        System.out.println("hello "+xyz);
        String output = "";
        for(int i=0;i<5;i++){
            output+="hello "+xyz+"<br/>";
        }
        return output;
    }



    @GetMapping("/bye")
    public String sayBye(){
        return "Bye!!!";
    }
}
