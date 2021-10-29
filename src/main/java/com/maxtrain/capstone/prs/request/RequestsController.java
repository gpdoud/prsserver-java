package com.maxtrain.capstone.prs.request;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestsController {
	@Autowired
	private RequestRepository reqRepo;
	
	// Custom Methods
	
	@GetMapping("reviews/{userId}")
	public ResponseEntity<List<Request>> GetRequestsInReviewNotUserId(@PathVariable int userId) {
		var requests = reqRepo.findByStatusAndUserIdNot("REVIEW", userId);
		return new ResponseEntity<List<Request>>(requests, HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("review/{id}")
	public ResponseEntity SetRequestToReview(@PathVariable int id, @RequestBody Request request) {
		if(request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		var status = request.getTotal() <= 50 ? "APPROVED" : "REVIEW";
		request.setStatus(status);
		return ChangeRequest(request.getId(), request);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("approve/{id}")
	public ResponseEntity SetRequestToApproved(@PathVariable int id, @RequestBody Request request) {
		if(request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		request.setStatus("APPROVED");
		return ChangeRequest(request.getId(), request);
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("reject/{id}")
	public ResponseEntity SetRequestToRejected(@PathVariable int id, @RequestBody Request request) {
		if(request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		request.setStatus("REJECTED");
		return ChangeRequest(request.getId(), request);
	}
	// Standard Methods
	
	@GetMapping
	public ResponseEntity<Iterable<Request>> GetAllRequests() {
		var requests = reqRepo.findAll();
		return new ResponseEntity<Iterable<Request>>(requests, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<Request> GetRequestById(@PathVariable int id) {
		var request = reqRepo.findById(id);
		if(request.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Request>(request.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Request> CreateRequest(@RequestBody Request request) {
		if(request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(request.getId() != 0) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		var newReq = reqRepo.save(request);
		return new ResponseEntity<Request>(newReq, HttpStatus.CREATED);
	}
	
	@SuppressWarnings("rawtypes")
	@PutMapping("{id}")
	public ResponseEntity ChangeRequest(@PathVariable int id, @RequestBody Request request) {
		if(request == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if(request.getId() != id) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		reqRepo.save(request);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("{id}")
	public ResponseEntity RemoveRequest(@PathVariable int id) {
		var requct = reqRepo.findById(id);
		if(requct.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		reqRepo.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
