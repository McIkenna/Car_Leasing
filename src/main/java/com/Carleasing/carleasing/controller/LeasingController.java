package com.Carleasing.carleasing.controller;


import com.Carleasing.carleasing.model.Order;
import com.Carleasing.carleasing.service.LeasingService;
import com.Carleasing.carleasing.service.MapErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
@RequestMapping("/api/order")
@CrossOrigin
public class LeasingController {
    @Autowired
    private LeasingService leasingService;

    @Autowired
    private MapErrorService mapErrorService;

    @PostMapping("/{makeId}/{vehicleId}")
    public ResponseEntity<?> saveOrder(@RequestBody Order order, @PathVariable String makeId, @PathVariable String vehicleId, BindingResult result){
        ResponseEntity<?> errorMap = mapErrorService.MapErrorService(result);
        if(errorMap != null) return errorMap;
        Order ord = leasingService.saveOrder(order, makeId, vehicleId);
        return new ResponseEntity<Order>(ord, HttpStatus.CREATED);
    }

    @GetMapping("/{leasingId}")
    public Order findOrder(@PathVariable String leasingId){
        return leasingService.findOrderById(leasingId);
    }

    @DeleteMapping("/{leasingId}")
    public String deleteOrder(@PathVariable String leasingId){
        return leasingService.deleteOrder(leasingId);
    }

    @PutMapping("/updateOrder")
    public String updateOrder(@RequestBody Order order){
        return leasingService.updateOrder(order);
    }
}
