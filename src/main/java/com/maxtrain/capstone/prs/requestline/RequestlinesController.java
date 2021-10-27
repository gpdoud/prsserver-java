package com.maxtrain.capstone.prs.requestline;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maxtrain.capstone.prs.request.RequestRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/requestlines")
public class RequestlinesController {
	@Autowired
	private RequestlineRepository reqlRepo;
	@Autowired
	private RequestRepository reqRepo;

	// Custom Methods
	
	private void RecalculateRequestTotal(int requestId) throws Exception {
		var optRequest = reqRepo.findById(requestId);
		if(optRequest.isEmpty()) {
			throw new Exception("RecalculateRequestTotal received an invalid request id!");
		}
		var request = optRequest.get();
		var requestlines = reqlRepo.findRequestlineByRequestId(requestId);
		var total = BigDecimal.ZERO;
		for(var requestline : requestlines) {
			var quantity = BigDecimal.valueOf(requestline.getQuantity());
			var price = requestline.getProduct().getPrice();
			total = total.add(quantity.multiply(price));
		}
		request.setTotal(total);
		reqRepo.save(request);
	}
	
	// Standard Methods
	
	@GetMapping
	public ResponseEntity<Iterable<Requestline>> GetAllRequestlines() {
		var requestlines = reqlRepo.findAll();
		return new ResponseEntity<Iterable<Requestline>>(requestlines, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Requestline> GetRequestlineById(@PathVariable int id) {
		var requestline = reqlRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Requestline>(requestline.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Requestline> CreateRequestline(@RequestBody Requestline requestline) throws Exception {
		if(requestline == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var newReq = reqlRepo.save(requestline);
		RecalculateRequestTotal(requestline.getRequest().getId());
		return new ResponseEntity<Requestline>(newReq, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity ChangeRequestline(@PathVariable int id, @RequestBody Requestline requestline) throws Exception {
		if(requestline == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(requestline.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		reqlRepo.save(requestline);
		RecalculateRequestTotal(requestline.getRequest().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity RemoveRequestline(@PathVariable int id) throws Exception {
		var requestline = reqlRepo.findById(id);
		if(requestline.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqlRepo.deleteById(id);
		RecalculateRequestTotal(requestline.get().getRequest().getId());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
