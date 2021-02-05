package com.Carleasing.carleasing.model;

import com.Carleasing.carleasing.util.State;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@DynamoDBDocument
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {

    @DynamoDBAttribute
    public String city;
    @DynamoDBAttribute
    public String street;
    @DynamoDBAttribute
    public State state;
    @DynamoDBAttribute
    public int zipCode;
}
