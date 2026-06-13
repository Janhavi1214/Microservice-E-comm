package com.day8.OrderService.repo;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.day8.OrderService.entity.Order;


public interface OrderRepo extends JpaRepository<Order, Integer> {

    @Query("FROM Order o WHERE o.customer.custId = :cid")
    List<Order> findByCustId(@Param("cid") int cid);

	Order save(com.day8.OrderService.entity.Order order);
}
