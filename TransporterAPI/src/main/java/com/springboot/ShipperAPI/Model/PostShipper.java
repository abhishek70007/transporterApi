package com.springboot.ShipperAPI.Model;

import javax.validation.constraints.Email;
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
	@NotBlank(message = "Email can not be blank!")
	@Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
			flags = Pattern.Flag.CASE_INSENSITIVE, message = "Please enter a valid email")
	private String emailId;
	private String gst;
	private String companyStatus;
	private String kyc;
}
