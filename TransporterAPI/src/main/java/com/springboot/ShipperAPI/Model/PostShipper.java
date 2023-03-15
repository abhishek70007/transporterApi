package com.springboot.ShipperAPI.Model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostShipper {
	private String shipperName;
	private String companyName;
	private String shipperLocation;
	@NotBlank(message = "Phone no. cannot be blank!")
	@Pattern(regexp="(^[6-9]\\d{9}$)", message="Please enter a valid mobile number") 
	private String phoneNo;
	private String kyc;
}
