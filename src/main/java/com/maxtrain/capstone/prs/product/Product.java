package com.maxtrain.capstone.prs.product;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.maxtrain.capstone.prs.vendor.Vendor;

@Entity
@Table(name = "products", uniqueConstraints=@UniqueConstraint(name = "UIDX_partNbr", columnNames = {"partnbr"}))
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(length=30, nullable=false)
	private String partnbr;
	@Column(length=30, nullable=false)
	private String name;
	private BigDecimal price;
	@Column(length=30, nullable=false)
	private String Unit = "Each";
	@Column(length=255)
	private String photopath;
	
	@ManyToOne(optional=false)
	@JoinColumn(name = "vendorId")
	private Vendor vendor;
	
	public Product() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPartnbr() {
		return partnbr;
	}

	public void setPartnbr(String partnbr) {
		this.partnbr = partnbr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getPhotopath() {
		return photopath;
	}

	public void setPhotopath(String photopath) {
		this.photopath = photopath;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	

	
}
