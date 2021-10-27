package com.maxtrain.capstone.prs.requestline;

import javax.persistence.*;

import com.maxtrain.capstone.prs.product.Product;
import com.maxtrain.capstone.prs.request.Request;

@Entity(name = "requestlines")
public class Requestline {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private int quantity;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="requestId")
	private Request request;

	@ManyToOne(optional=false)
	@JoinColumn(name="productId")
	private Product product;
	
	public Requestline() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	
}
