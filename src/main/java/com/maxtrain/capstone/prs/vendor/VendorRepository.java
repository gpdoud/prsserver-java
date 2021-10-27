package com.maxtrain.capstone.prs.vendor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer>{
	Vendor findByCode(String code);
}
