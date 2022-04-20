package com.example.payment.order;

import com.example.payment.order.dto.OrderRequest;
import com.example.payment.order.model.Order;
import com.example.payment.order.model.OrderStatus;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    
    @Value(value = "${razorpay.key}")
    private String key;
    @Value(value = "${razorpay.secret}")
    private String secret;

    ResponseEntity<?> createOrder(OrderRequest request) {
        // TODO : create order from Razorpay check for success or failure , set status accordingly and return httpresp
    	
    	double amt = Double.parseDouble(request.getPrice());
    	amt *= 100;
    	//System.out.println(amt);
    	
    	
        Order order = Order.builder()
                .name(request.getName())
                .price(amt)
                .status(OrderStatus.PROCESSING)
                .build();
        orderRepository.save(order);
        
        try {
        	       	
        	RazorpayClient razorpayClient = new RazorpayClient(key, secret);
        	JSONObject options = new JSONObject();
        	options.put("amount", amt);
        	options.put("currency", "INR");
        	options.put("receipt", "txn_123456");
        	com.razorpay.Order order1 = razorpayClient.Orders.create(options);
        	
        	String orderId = order1.get("id");
        	order.setOrderId(orderId);
        	order.setStatus(OrderStatus.SUCCESS);
        	orderRepository.save(order);
        	
        } catch(RazorpayException e) {        	
        	order.setStatus(OrderStatus.FAILED);
        	orderRepository.save(order);
        	return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Order Not Created");
        	
        }  
        
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}	