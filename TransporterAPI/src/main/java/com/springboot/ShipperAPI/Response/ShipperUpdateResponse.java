package com.springboot.ShipperAPI.Response;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipperUpdateResponse {
	private String status;
	private String message;

	private String shipperId;
	private String shipperName;
	private String companyName;
	private String phoneNo;
	private String emailId;
	private String gst;
	private String companyStatus;
	private String kyc;
	private String shipperLocation;
	private boolean companyApproved;
	private boolean accountVerificationInProgress;
	public Timestamp timestamp;
}
