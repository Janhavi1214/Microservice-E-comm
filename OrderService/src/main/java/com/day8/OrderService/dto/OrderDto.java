package com.day8.OrderService.dto;

public class OrderDto {

	private int cid;
	private int[] pcodes;
	
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public int[] getPcodes() {
		return pcodes;
	}
	public void setPcodes(int[] pcodes) {
		this.pcodes = pcodes;
	}
	
	
}

