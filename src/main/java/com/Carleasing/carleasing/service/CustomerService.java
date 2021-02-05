package com.Carleasing.carleasing.service;

import com.Carleasing.carleasing.exception.CustomerException;
import com.Carleasing.carleasing.model.Customer;
import com.Carleasing.carleasing.repository.CustomerRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerService implements CustomerRepository {

    @Autowired
    private DynamoDBMapper mapper;

    public Customer saveCustomer(Customer customer) {
            mapper.save(customer);
            return  customer;
    }

    public Customer findCustomerById(String customerId) {
       try{ return mapper.load(Customer.class, customerId);

       } catch (Exception e){
           throw new CustomerException("This customer '" + customerId + " does not exist");
       }
    }

    public String deleteCustomer(String customerId){
        mapper.delete(customerId);
        return "Customer deleted !!";
    }

    public String updateCustomer(Customer customer) {
        try {
            mapper.save(customer, buildExpression(customer));
            return "record Updated";
        }
        catch(Exception e){
            throw new CustomerException("This user '" + customer.getPerson().getLastName() + "' already exist");
        }
    }

    private DynamoDBSaveExpression buildExpression(Customer customer){
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("customerId", new ExpectedAttributeValue((new AttributeValue().withS(customer.getCustomerId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }
}
