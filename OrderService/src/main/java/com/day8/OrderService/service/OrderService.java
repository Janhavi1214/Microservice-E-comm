package com.day8.OrderService.service;

import java.util.List;

import com.day8.OrderService.entity.Order;
import com.day8.OrderService.vo.OrderVO;



public interface OrderService {

	Order save(OrderVO order);
	
	OrderVO findByNo(int ordNo);
	
	List<OrderVO> findByCustomer(int cid);

	//Order save(Order order);
}

