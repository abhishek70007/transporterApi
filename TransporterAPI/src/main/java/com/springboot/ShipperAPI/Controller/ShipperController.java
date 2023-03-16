package com.springboot.ShipperAPI.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.ShipperAPI.Entity.Shipper;
import com.springboot.ShipperAPI.Model.PostShipper;
import com.springboot.ShipperAPI.Model.UpdateShipper;
import com.springboot.ShipperAPI.Service.ShipperService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
public class ShipperController {

	@Autowired
	ShipperService service;

//	@GetMapping("/home")
//	public String home() {
//		return "Welcome to shipperApi git actions test 2...!!!";
//	}


	@PostMapping("/shipper")
	public ResponseEntity<Object> addShipper(@Valid @RequestBody PostShipper postShipper) {
		log.info("Post Controller Started");
		return new ResponseEntity<>(service.addShipper(postShipper),HttpStatus.CREATED);
	}

	@GetMapping("/shipper")
	public ResponseEntity<List<Shipper>> getShippers(
			@RequestParam(required = false) Boolean companyApproved,
			@RequestParam(required = false) String phoneNo,
			@RequestParam(required = false) String emailId,
			@RequestParam(required = false) Integer pageNo) {
		log.info("Get with Params Controller Started");
		return new ResponseEntity<>(service.getShippers(companyApproved, phoneNo, emailId, pageNo),HttpStatus.OK);

	}

	@GetMapping("/shipper/{shipperId}")
	private ResponseEntity<Object> getOneShipper(@PathVariable String shipperId) {
		log.info("Get by shiperId Controller Started");
		return new ResponseEntity<>(service.getOneShipper(shipperId),HttpStatus.OK);
	}


	@PutMapping("/shipper/{shipperId}")
	public ResponseEntity<Object> updateShipper(@PathVariable String shipperId, @RequestBody UpdateShipper updateShipper){
		log.info("Put Controller Started");
		return new ResponseEntity<>( service.updateShipper(shipperId, updateShipper),HttpStatus.OK);
	}


	@DeleteMapping("/shipper/{shipperId}")
	public ResponseEntity<Object> deleteShipper(@PathVariable String shipperId){
		log.info("Delete Controller Started");
		service.deleteShipper(shipperId);
		return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
	}

}
