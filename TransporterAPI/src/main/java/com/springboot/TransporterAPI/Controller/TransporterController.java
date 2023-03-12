package com.springboot.TransporterAPI.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Exception.EntityNotFoundException;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Services.TransporterService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class TransporterController {

	@Autowired
	private TransporterService service;

	@GetMapping("/home")
	public String home() {
		return "Welcome to transporterApi git action check 2...!!!";
	}

	@PostMapping("/transporter")
	public ResponseEntity<Object> addTransporter(@Valid @RequestBody  PostTransporter transporter) {
		log.info("Post Controller Started");
		return new ResponseEntity<>(service.addTransporter(transporter),HttpStatus.CREATED);
	}


	@GetMapping("/transporter")
	public ResponseEntity<List<Transporter>> getTransporters(
			@RequestParam(required = false) Boolean transporterApproved,
			@RequestParam(required = false) Boolean companyApproved,
			@RequestParam(required = false) String phoneNo,
			@RequestParam(required = false) Integer pageNo){
		log.info("Get with Params Controller Started");
		return new ResponseEntity<>(service.getTransporters(transporterApproved, companyApproved, phoneNo, pageNo),HttpStatus.OK);
	}

	@GetMapping("/transporter/{transporterId}")
	private ResponseEntity<Object> getOneTransporter(@PathVariable String transporterId) {
		log.info("Get by transporterId Controller Started");
		return new ResponseEntity<>( service.getOneTransporter(transporterId),HttpStatus.OK);
	}


	@PutMapping("/transporter/{transporterId}")
	public ResponseEntity<Object> updateTransporter(@PathVariable String transporterId, @RequestBody UpdateTransporter transporter){
		log.info("Put Controller Started");
		return new ResponseEntity<>(service.updateTransporter(transporterId, transporter),HttpStatus.OK);
	}


	@DeleteMapping("/transporter/{transporterId}")
	public ResponseEntity<Object> deleteTransporter(@PathVariable String transporterId){
		log.info("Delete Controller Started");
		service.deleteTransporter(transporterId);
		return new ResponseEntity<>("Sucessfully deleted",HttpStatus.OK);
	}

}
