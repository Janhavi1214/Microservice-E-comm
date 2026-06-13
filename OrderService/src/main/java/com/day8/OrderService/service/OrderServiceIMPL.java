package com.day8.OrderService.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.day8.CustomerService.vo.CustomerVO;
import com.day8.OrderService.dto.OrderDto;
import com.day8.OrderService.entity.Order;
import com.day8.OrderService.repo.OrderRepo;
import com.day8.OrderService.vo.OrderVO;
import com.day8.ProductService.vo.ProductVO;

@Service
public class OrderServiceImpl implements OrderService{
     @Autowired
	private OrderRepo repo;
     
     @Value("${product-service.url}")
     private String prodServiceUrl;
     
     @Value("${customer-service.url}")
     private String custServiceUrl;
     
     @Autowired
     private RestTemplate rest;
     
 	@Override
 	public Order save(OrderVO order) {
 		order.setOrdDate(LocalDate.now());
 		CustomerVO cust=rest.getForObject(custServiceUrl+"/"+order.getCustId(), CustomerVO.class);
 		
 		ProductVO item=rest.getForObject(
 			    prodServiceUrl+"/"+order.getCode(),
 			    ProductVO.class
 			);
 		
 		order.setAmount(item.getPrice());
 		Order resp=repo.save(order);
 		OrderVO vo=OrderVO.transform(order);
 		vo.setCustomer(cust);
 		vo.setItem(item);
 		return vo;

 	}
 	
 	@Override
	public OrderVO findByNo(int ordNo) {
		Order order=repo.findById(ordNo).get();		
		
        CustomerVO cust=rest.getForObject(custServiceUrl+"/"+order.getCustId(), CustomerVO.class);
		
        ProductVO item=rest.getForObject(
        	    prodServiceUrl+"/"+order.getCode(),
        	    ProductVO.class
        	);
		
		OrderVO vo = OrderVO.transform(order);
		vo.setCustomer(cust);
		vo.setItem(item);
		return vo;
	}
	
 	@Override
	public List<OrderVO> findByCustomer(int cid) {
	    List<Order> orders = repo.findByCustId(cid);
	    List<OrderVO> vos = new ArrayList<OrderVO>();
	    
	    for(Order order : orders) {
	        OrderVO vo = OrderVO.transform(order);
	        CustomerVO cust = rest.getForObject(
	                custServiceUrl + "/" + order.getCustId(),
	                CustomerVO.class);

	        ProductVO item = rest.getForObject(
	                prodServiceUrl + "/" + order.getCode(),
	                ProductVO.class);

	        vo.setCustomer(cust);
	        vo.setItem(item);

	        vos.add(vo);
	    }
	    return vos;
	}
}