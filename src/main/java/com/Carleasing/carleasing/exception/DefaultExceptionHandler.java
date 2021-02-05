package com.Carleasing.carleasing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleCustomerIdException(CustomerException ex, WebRequest request){
        CustomerException exceptionResponse = new CustomerException(ex.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleVehicleIdException(VehicleException ex, WebRequest request){
        VehicleException exceptionResponse = new VehicleException(ex.getMessage());

        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
