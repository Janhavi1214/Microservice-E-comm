package com.day8.CustomerService.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class Customer {

	@Id @Column(name = "custId")
	@Min(value = 100, message = "Customer ID must be greater than 100")
	private int custId;
	
	@Column(length = 30)
	@Size(min = 5, message = "Customer name must be min 5 chars")
	private String custName;
	
	@Column(unique = true, length = 10)
	@Size(min = 10, message = "Mobile number must be of 10 digits")
	private String mobile;
	
	//@OneToMany(mappedBy = "customer")
	//private List<Order> orders;

	public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

//	public List<Order> getOrders() {
//		return orders;
//	}

//	public void setOrders(List<Order> orders) {
//		this.orders = orders;
//	}
	
	
	
}

