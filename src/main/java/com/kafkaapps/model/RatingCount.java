package com.kafkaapps.model;

public class RatingCount {
	 public long count;
     public double total;
     
     public RatingCount() {}

     public RatingCount(long count, double total) {
		super();
		this.count = count;
		this.total = total;
	}



	public long getCount() {
		return count;
	}



	public void setCount(long count) {
		this.count = count;
	}



	public double getTotal() {
		return total;
	}



	public void setTotal(double total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "RatingCount [count=" + count + ", total=" + total + "]";
	}





}
