package com.day8.OrderService.vo;


import java.time.LocalDate;
import java.util.List;

import com.day8.CustomerService.vo.CustomerVO;
import com.day8.OrderService.entity.Order;
import com.day8.ProductService.vo.ProductVO;

public class OrderVO {

    private int orderId;
    private LocalDate ordDate;
    private ProductVO item;
    public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public LocalDate getOrdDate() {
		return ordDate;
	}
	public void setOrdDate(LocalDate ordDate) {
		this.ordDate = ordDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public CustomerVO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerVO customer) {
		this.customer = customer;
	}
	public List<ProductVO> getItems() {
		return items;
	}
	public void setItems(List<ProductVO> items) {
		this.items = items;
	}
	private double amount;

    private CustomerVO customer;
    private List<ProductVO> items;
    
    public ProductVO getItem() {
    		return item;
    }
    
    public void setItem(ProductVO item) {
    		this.item = item;
    }
    
    public static final OrderVO transform(Order order) {
        OrderVO vo = new OrderVO();

        vo.setOrderId(order.getOrderNum());
        vo.setOrdDate(order.getOrderDate());
        vo.setAmount(order.getAmt());

        return vo;
    }

}