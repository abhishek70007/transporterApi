package com.springboot.TransporterAPI.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTransporter {
	private String phoneNo;
	private String transporterLocation;
	private String transporterName;
	private String companyName;
	private String kyc;
	private String emailId;
	private Boolean companyApproved;
	private Boolean transporterApproved;
	private Boolean accountVerificationInProgress;
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
	public Boolean getCompanyApproved() {
		return companyApproved;
	}
	public void setCompanyApproved(Boolean companyApproved) {
		this.companyApproved = companyApproved;
	}
	public Boolean getTransporterApproved() {
		return transporterApproved;
	}
	public void setTransporterApproved(Boolean transporterApproved) {
		this.transporterApproved = transporterApproved;
	}
	public Boolean getAccountVerificationInProgress() {
		return accountVerificationInProgress;
	}
	public void setAccountVerificationInProgress(Boolean accountVerificationInProgress) {
		this.accountVerificationInProgress = accountVerificationInProgress;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
