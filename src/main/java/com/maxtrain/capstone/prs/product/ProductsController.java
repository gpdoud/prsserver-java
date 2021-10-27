package com.maxtrain.capstone.prs.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductsController {
	@Autowired
	private ProductRepository prodRepo;
	
	@GetMapping
	public ResponseEntity<Iterable<Product>> GetAllProducts() {
		var products = prodRepo.findAll();
		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Product> GetProductsByPk(@PathVariable int id) {
		var product = prodRepo.findById(id);
		if(product.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Product>(product.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Product> CreateProduct(@RequestBody Product product) {
		if(product == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var newProd = prodRepo.save(product);
		return new ResponseEntity<Product>(newProd, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity ChangeProduct(@PathVariable int id, @RequestBody Product product) {
		if(product == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(product.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		prodRepo.save(product);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity RemoveProduct(@PathVariable int id) {
		var product = prodRepo.findById(id);
		if(product.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		prodRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
