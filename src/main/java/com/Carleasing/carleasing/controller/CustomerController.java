package com.Carleasing.carleasing.controller;

import com.Carleasing.carleasing.model.Customer;
import com.Carleasing.carleasing.model.LoginRequest;
import com.Carleasing.carleasing.model.LoginResponse;
import com.Carleasing.carleasing.security.JwtUtil;
import com.Carleasing.carleasing.service.CustomerService;
import com.Carleasing.carleasing.service.MapErrorService;
import com.Carleasing.carleasing.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MapErrorService mapErrorService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginRequest loginRequest, BindingResult result) throws Exception {

        try{
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
    );

        }catch(BadCredentialsException e){
                throw new Exception("Incorrect username or password", e);
        }
final UserDetails userDetails = userDetailsService
        .loadUserByUsername(loginRequest.getUsername());
        final String token = "Bearer " + jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new LoginResponse(true,token));
    }

    @PostMapping("/saveCustomer")
    public ResponseEntity<?> saveCustomer(@RequestBody Customer customer, BindingResult result){
        ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
        if(errorMap != null) return errorMap;
       Customer cus = customerService.saveCustomer(customer);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/user/{customerId}")
    public Customer findCustomer(@PathVariable String customerId){
        return customerService.findCustomerById(customerId);
    }

    @DeleteMapping("/{customerId}")
    public String deleteCustomer(@PathVariable String customerId){
        return customerService.deleteCustomer(customerId);
    }

    @PutMapping("/updateCustomer")
    public String updateCustomer(@RequestBody Customer customer){
        return customerService.updateCustomer(customer);
    }
}
