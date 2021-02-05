package com.Carleasing.carleasing.exception;

import lombok.Data;

@Data
public class CustomerIdExceptionResponse {
    private String customerId;

    public CustomerIdExceptionResponse(String customerId) {
        this.customerId = customerId;
    }
}
