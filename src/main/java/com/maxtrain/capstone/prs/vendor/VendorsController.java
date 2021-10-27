package com.maxtrain.capstone.prs.vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorsController {
	@Autowired
	private VendorRepository vendRepo;
	
	// Custom Methods
	
	@GetMapping("code/{code}")
	public ResponseEntity<Vendor> GetByCode(@PathVariable String code) {
		var vendor = vendRepo.findByCode(code);
		if(vendor == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendor, HttpStatus.OK);
	}
	
	// Basic Methods
	
	@GetMapping
	public ResponseEntity<Iterable<Vendor>> GetAllVendors() {
		var vendors = vendRepo.findAll();
		return new ResponseEntity<Iterable<Vendor>>(vendors, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Vendor> GetVendorsByPk(@PathVariable int id) {
		var vendor = vendRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Vendor>(vendor.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Vendor> CreateVendor(@RequestBody Vendor vendor) {
		if(vendor == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var newVend = vendRepo.save(vendor);
		return new ResponseEntity<Vendor>(newVend, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity ChangeVendor(@PathVariable int id, @RequestBody Vendor vendor) {
		if(vendor == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(vendor.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		vendRepo.save(vendor);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity RemoveVendor(@PathVariable int id) {
		var vendor = vendRepo.findById(id);
		if(vendor.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		vendRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
