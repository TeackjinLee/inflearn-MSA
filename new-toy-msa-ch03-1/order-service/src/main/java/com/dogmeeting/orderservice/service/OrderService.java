package com.dogmeeting.orderservice.service;

import com.dogmeeting.orderservice.dto.OrderDto;
import com.dogmeeting.orderservice.jpa.OrderEntity;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDetails);
    OrderDto getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrdersByUserId(String userId);
}
