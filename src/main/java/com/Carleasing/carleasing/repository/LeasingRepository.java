package com.Carleasing.carleasing.repository;

import com.Carleasing.carleasing.model.Order;

public interface LeasingRepository {
    Order findOrderById(String leasingId);
    Order saveOrder(Order order, String makeId, String vehicleId);
    String updateOrder(Order order);
    String deleteOrder(String leasingId);

    Iterable<Order> findAll();
}
