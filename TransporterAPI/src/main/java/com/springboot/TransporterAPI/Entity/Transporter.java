package com.springboot.TransporterAPI.Entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Table(name = "Transporter")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transporter {
	@Id
	private String transporterId;
	@Column(unique=true)
	@NotBlank(message = "Phone no. cannot be blank!")
	//	"^[6-9]\\d{9}$", "(^$|[0-9]{10})"
	@Pattern(regexp="(^[6-9]\\d{9}$)", message="Please enter a valid mobile number") 
	private String phoneNo;
	private String transporterName;
	private String companyName;
	private String transporterLocation;
	private String kyc;
	private boolean companyApproved;
	private String emailId;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean transporterApproved;

	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean accountVerificationInProgress;
	
	@CreationTimestamp
	public Timestamp timestamp;

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

	public boolean isCompanyApproved() {
		return companyApproved;
	}

	public void setCompanyApproved(boolean companyApproved) {
		this.companyApproved = companyApproved;
	}

	public boolean isTransporterApproved() {
		return transporterApproved;
	}

	public void setTransporterApproved(boolean transporterApproved) {
		this.transporterApproved = transporterApproved;
	}

	public boolean isAccountVerificationInProgress() {
		return accountVerificationInProgress;
	}

	public void setAccountVerificationInProgress(boolean accountVerificationInProgress) {
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
