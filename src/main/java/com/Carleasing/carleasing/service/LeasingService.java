package com.Carleasing.carleasing.service;

import com.Carleasing.carleasing.exception.CustomerException;
import com.Carleasing.carleasing.model.Order;
import com.Carleasing.carleasing.model.Vehicle;
import com.Carleasing.carleasing.repository.LeasingRepository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LeasingService implements LeasingRepository {
    @Autowired
    private DynamoDBMapper mapper;

    @Autowired
    private VehicleService vehicleService;
    @Override
    public Order saveOrder(Order order, String makeId, String vehicleId) {
        Vehicle vehicle = vehicleService.findVehicleByMakeId(makeId, vehicleId);


        order.setCarDetails(" Model: " + vehicle.getModel() + " Make: "+ vehicle.getMake() + " Style: " + vehicle.getStyle() + " TrimLevel: " + vehicle.getTrimLevel() + " Year: "+ vehicle.getYear() );
        double leasingPrice =  CalLeasePlan(vehicle.getStyle(), order.getPlan(), order.getQuantity(), order.getPeriod(), order.getCreditScore(), vehicle.getCarValue());
        order.setLeasePrice(leasingPrice);
        order.setValue(vehicle.getCarValue());
        mapper.save(order);
        return order;
    }
    @Override
    public Order findOrderById(String leasingId) {
        try{ return mapper.load(Order.class, leasingId);

        } catch (Exception e){
            throw new CustomerException("This order '" + leasingId + " does not exist");
        }
    }

    @Override
    public String updateOrder(Order order) {
        try {
            mapper.save(order, buildExpression(order));
            return "record Updated";
        }
        catch(Exception e){
            throw new CustomerException("This order '" + order.getLeasingId()+ "' already exist");
        }
    }

    @Override
    public String deleteOrder(String leasingId) {
        mapper.delete(leasingId);
            return "Order deleted !!";
    }

    @Override
    public Iterable<Order> findAll() {
        return mapper.scan(Order.class, new DynamoDBScanExpression());
    }

    private DynamoDBSaveExpression buildExpression(Order order){
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedMap=new HashMap<>();
        expectedMap.put("leasingId", new ExpectedAttributeValue((new AttributeValue().withS(order.getLeasingId()))));
        dynamoDBSaveExpression.setExpected(expectedMap);
        return dynamoDBSaveExpression;
    }

    public double calcValue(String style, double carValue){
        if(style.equals("BUS")) {
            carValue += 3000;
        }
        else if(style.equals("SUV")) {
            carValue += 2500;
        }
        else if(style.equals("VAN")) {
            carValue += 2000;
        }
        else if(style.equals("TRUCK") ) {
            carValue += 1500;
        }
        return carValue;
    }

    public double CalLeasePlan(String style, String plan, int quantity, int period, int creditScore, double carValue){
        double carWorth = calcValue(style, carValue);
        double leasePrice = carWorth / 60;
        if (creditScore >= 500 && period > 0 && quantity > 0 && plan != null) {
            if (plan.equals("Monthly")) {
                leasePrice *= period * quantity;
            } else if (plan.equals("Weekly")) {
                leasePrice *= period * quantity / 4.0;
            } else if (plan.equals("Daily")) {
                leasePrice *= period * quantity / 30.0;
            } else if (plan.equals("Hourly")) {
                leasePrice *= period * quantity / 720.0;
            }else if (plan.equals(" ") || plan.equals("")) {
                throw new CustomerException("Input is Invalid");
            }

        } else if(creditScore < 500){
            throw new CustomerException("Credit Score is too low");
        }
        else if(period < 0 ){
            throw new CustomerException("Enter a multiplier");
        }
        else if(quantity < 0){
            throw new CustomerException("Enter your quantity");
        }
        else {
            throw new CustomerException("One or more of your input is incorrect");
        }
        return leasePrice;
    }
}
