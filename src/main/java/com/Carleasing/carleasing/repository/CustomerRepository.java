package com.Carleasing.carleasing.repository;

import com.Carleasing.carleasing.model.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository{

    Customer findCustomerById(String customerId);
    Customer saveCustomer(Customer customer);
    String deleteCustomer(String customerId);
    String updateCustomer(Customer customer);

}
