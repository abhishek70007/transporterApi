package com.springboot.TransporterAPI.Services;

import java.util.List;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Exception.EntityNotFoundException;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

public interface TransporterService {

	public TransporterCreateResponse addTransporter(PostTransporter postTransporter);

	public List<Transporter> getTransporters(Boolean transporterApproved, Boolean companyApproved, String phoneNo, Integer pageNo,String emailId);

	public TransporterUpdateResponse updateTransporter(String transporterId, UpdateTransporter updatetransporter);

	public void deleteTransporter(String transporterId);

	public Transporter getOneTransporter(String transporterId);

}
