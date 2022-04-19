package com.example.payment.order;

import com.example.payment.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // CRUD -> Create Read Update Delete. CrudRepository is the parent off JpaRepository
    // all the methods of CrudRepository return Object type whereas JpaRepository can return
    // List<> or Entity or Optional<>

}
