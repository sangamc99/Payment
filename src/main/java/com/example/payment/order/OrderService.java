package com.example.payment.order;

import com.example.payment.order.dto.OrderRequest;
import com.example.payment.order.model.Order;
import com.example.payment.order.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.orderRepository = repository;
    }

    ResponseEntity<?> createOrder(OrderRequest request) {
        // TODO : create order from Razorpay check for success or failure , set status accordingly and return httpresp
        Order order = Order.builder()
                .name(request.getName())
                .price(Double.parseDouble(request.getPrice()))
                .status(OrderStatus.PROCESSING)
                .build();

        orderRepository.save(order);
        return ResponseEntity.status(HttpStatus.OK).body("Order Successfully Created");
    }


}
