package com.kafkaapps.model;

public class Review {
	public String productId;
    public String reviewer;
    public double rating;
    
    public Review() {}
	public Review(String productId, String reviewer, double rating) {
		super();
		this.productId = productId;
		this.reviewer = reviewer;
		this.rating = rating;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	@Override
	public String toString() {
		return "Review [productId=" + productId + ", reviewer=" + reviewer + ", rating=" + rating + "]";
	}
    
	
    
}
