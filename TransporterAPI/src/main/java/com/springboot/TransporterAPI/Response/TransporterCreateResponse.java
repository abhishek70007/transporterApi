package com.springboot.TransporterAPI.Response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransporterCreateResponse {
	private String status;
	private String message;
	private String emailId;
	private String transporterId;
	private String phoneNo;
	private String transporterName;
	private String companyName;
	private String transporterLocation;
	private String kyc;
	private Boolean companyApproved;
	private Boolean transporterApproved;
	private Boolean accountVerificationInProgress;
	public Timestamp timestamp;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTransporterId() {
		return transporterId;
	}
	public void setTransporterId(String transporterId) {
		this.transporterId = transporterId;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
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
	public String getTransporterLocation() {
		return transporterLocation;
	}
	public void setTransporterLocation(String transporterLocation) {
		this.transporterLocation = transporterLocation;
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
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
