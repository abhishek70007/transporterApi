package com.springboot.TransporterAPI.Model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostTransporter {

	@NotBlank(message = "Phone no. cannot be blank!")
	@Pattern(regexp="(^[6-9]\\d{9}$)", message="Please enter a valid mobile number") 
	private String phoneNo;
	private String transporterLocation;
	private String transporterName;
	private String companyName;
	private String kyc;
	private String emailId;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailID(String emailId) {
		this.emailId = emailId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getTransporterLocation() {
		return transporterLocation;
	}
	public void setTransporterLocation(String transporterLocation) {
		this.transporterLocation = transporterLocation;
	}
	public String getTransporterName() {
		return transporterName;
	}
	public void setTransporterName(String transporterName) {
		this.transporterName = transporterName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getKyc() {
		return kyc;
	}
	public void setKyc(String kyc) {
		this.kyc = kyc;
	}

}
