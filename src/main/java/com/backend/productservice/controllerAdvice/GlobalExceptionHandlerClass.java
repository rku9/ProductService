package com.backend.productservice.controllerAdvice;

import com.backend.productservice.dtos.ExceptionDto;
import com.backend.productservice.exceptions.NoProductException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlerClass {


    //! if we write this same block of code in some specific controller then this will only be thrown when the error
    //! happens in that controller and not anywhere else. And this controller method will get the higher priority.


    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<String> handleArithmeticException(){
        ResponseEntity<String> responseEntity = new ResponseEntity<>(
                "Something went wrong. From Controller Adivce.",
                HttpStatus.BAD_REQUEST
        );

        return responseEntity;
    }


    // we can also choose to return some more information other than just a simple string.
    //we can use dto.
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionDto> handleNullPException(){
        //create the exceptionDto object and then pass it to the responseEntity.
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Something went wrong. From Controller Advice.");
        exceptionDto.setResolution("Contact the admin.");

        return new ResponseEntity<>(
                exceptionDto,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(NoProductException.class)
    public ResponseEntity<ExceptionDto> handleNoProductException(NoProductException ex){
        //! way 1
//        ResponseEntity<String> response = new ResponseEntity<>(
//                "Enter a valid prodcut id!",
//                HttpStatus.BAD_GATEWAY
//        );

        //! way 2 - more info and not just a string. Change the return type to a dto.
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Wrong product id entered!");
        exceptionDto.setResolution("Enter an id other than "+ex.getId() );

        return new ResponseEntity<>(
                exceptionDto,
                HttpStatus.BAD_GATEWAY

        );
    }
}
