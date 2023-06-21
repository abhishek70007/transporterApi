package com.springboot.TransporterAPI.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.TransporterAPI.Exception.BusinessException;
import com.springboot.TransporterAPI.Exception.EntityNotFoundException;
import com.springboot.ShipperAPI.Entity.Shipper;
import com.springboot.TransporterAPI.Constants.CommonConstants;
import com.springboot.TransporterAPI.Dao.TransporterDao;
import com.springboot.TransporterAPI.Entity.Transporter;
import com.springboot.TransporterAPI.Model.PostTransporter;
import com.springboot.TransporterAPI.Model.UpdateTransporter;
import com.springboot.TransporterAPI.Response.TransporterCreateResponse;
import com.springboot.TransporterAPI.Response.TransporterUpdateResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransporterServiceImpl implements TransporterService {

	Logger log = org.slf4j.LoggerFactory.getLogger(TransporterServiceImpl.class);
	@Autowired
	private TransporterDao transporterdao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TransporterCreateResponse addTransporter(PostTransporter postTransporter) {
		log.info("addTransporter service is started");

		String temp="";
		Transporter transporter = new Transporter();
		TransporterCreateResponse response = new TransporterCreateResponse();

		Optional<Transporter> t = transporterdao.findByPhoneNo(postTransporter.getPhoneNo());
		if (t.isPresent()) {
			response.setTransporterId(t.get().getTransporterId());
			response.setPhoneNo(t.get().getPhoneNo());
			response.setTransporterName(t.get().getTransporterName());
			response.setTransporterLocation(t.get().getTransporterLocation());
			response.setCompanyName(t.get().getCompanyName());
			response.setKyc(t.get().getKyc());
			response.setTransporterApproved(t.get().isTransporterApproved());
			response.setCompanyApproved(t.get().isCompanyApproved());
			response.setAccountVerificationInProgress(t.get().isAccountVerificationInProgress());
			response.setMessage(CommonConstants.accountExist);
			response.setTimestamp(t.get().getTimestamp());
			response.setEmailId(t.get().getEmailId());
			return response;
		}

		temp="transporter:"+UUID.randomUUID();
		transporter.setTransporterId(temp);
		response.setTransporterId(temp);

		temp=postTransporter.getPhoneNo();
		transporter.setPhoneNo(temp);
		response.setPhoneNo(temp);

		temp=postTransporter.getTransporterName();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setTransporterName(temp.trim());
			response.setTransporterName(temp.trim());
		}

		temp=postTransporter.getCompanyName();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setCompanyName(temp.trim());
			response.setCompanyName(temp.trim());
		}

		temp=postTransporter.getTransporterLocation();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setTransporterLocation(temp.trim());
			response.setTransporterLocation(temp.trim());
		}

		temp=postTransporter.getKyc();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setKyc(temp.trim());
			response.setKyc(temp.trim());
		}
		
		temp=postTransporter.getEmailId();
		if(StringUtils.isNotBlank(temp)) {
			transporter.setEmailId(temp);
			response.setEmailId(temp);
		}

		transporter.setTransporterApproved(false);
		response.setTransporterApproved(false);

		transporter.setCompanyApproved(false);
		response.setCompanyApproved(false);

		transporter.setAccountVerificationInProgress(false);
		response.setAccountVerificationInProgress(false);

		transporterdao.save(transporter);
		log.info("transporter is saved to the database");

		response.setStatus(CommonConstants.pending);
		response.setMessage(CommonConstants.approveRequest);

		log.info("addTransporter response is returned");
		response.setTimestamp(transporter.getTimestamp());
		return response;
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public Transporter getOneTransporter(String transporterId) {
		log.info("getOneTransporter service is started");
		Optional<Transporter> S = transporterdao.findById(transporterId);
		if(S.isEmpty()) {
			throw new EntityNotFoundException(Transporter.class,"id",transporterId);
		}
		log.info("getOneTransporter response is returned");
		return S.get();
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public List<Transporter> getTransporters(Boolean transporterApproved, Boolean companyApproved, String phoneNo, Integer pageNo ,String emailId){
		log.info("getTransporters service is started");
		List<Transporter> list = null;
		if(pageNo == null) {
			pageNo = 0;
		}

		Pageable page = PageRequest.of(pageNo, 15, Sort.Direction.DESC, "timestamp");
		if((companyApproved != null) && (transporterApproved != null)) {
			list = transporterdao.findByTransporterCompanyApproved(transporterApproved, companyApproved, page);
			//Collections.reverse(list);
			return list;
		}

		if(transporterApproved != null) {
			list = transporterdao.findByTransporterApproved(transporterApproved, page);
			//Collections.reverse(list);
			return list;
		}
		if(companyApproved != null) {
			list = transporterdao.findByCompanyApproved(companyApproved, page);
			//Collections.reverse(list);
			return list;
		}
		if(phoneNo != null) {
			String validate = "^[6-9]\\d{9}$";
			Pattern pattern = Pattern.compile(validate);
			Matcher m = pattern.matcher(phoneNo);
			
			if(m.matches()) {
				if(transporterdao.findByPhoneNo(phoneNo).isPresent()) {
					list = List.of(transporterdao.findByPhoneNo(phoneNo).get());
					return list;
				}
				else {
					throw new EntityNotFoundException(Transporter.class, "phoneNo", phoneNo.toString());
				}
			}
			else {
				//throw new MethodArgumentNotValidException(null, null);
				throw new BusinessException("Invalid mobile number");
			}
			
		}
		if(emailId != null){
			String validate = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
			Pattern pattern = Pattern.compile(validate);
			Matcher m = pattern.matcher(emailId);
			if(m.matches()){
				if(transporterdao.findByEmailId(emailId).isPresent()) {
					list = List.of(transporterdao.findByEmailId(emailId).get());
					return list;
				}
				else {
					throw new EntityNotFoundException(Transporter.class, "emailId", emailId.toString());
				}
			}
			else{
				throw new BusinessException("Invalid email Id");
			}
		}

		list = transporterdao.getAll(page);
		//Collections.reverse(list);

		log.info("getTransporters response is returned");
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public TransporterUpdateResponse updateTransporter(String transporterId, UpdateTransporter updateTransporter) {
		log.info("updateTransporter service is started");

		TransporterUpdateResponse updateResponse = new TransporterUpdateResponse();

		Optional<Transporter> T = transporterdao.findById(transporterId);
		if(T.isEmpty())
			throw new EntityNotFoundException(Transporter.class, "id", transporterId.toString());

		String temp="";
		Transporter transporter = T.get();

		if (updateTransporter.getPhoneNo() != null) {			
			throw new BusinessException("Phone no can't be updated");
		}

		temp=updateTransporter.getTransporterName();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setTransporterName(temp.trim());
		}

		temp=updateTransporter.getTransporterLocation();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setTransporterLocation(temp.trim());
		}

		temp=updateTransporter.getCompanyName();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setCompanyName(temp.trim());
		}
		
		temp=updateTransporter.getEmailId();
		if (StringUtils.isNotBlank(temp)) {
			transporter.setEmailId(temp);
		}

		if (updateTransporter.getKyc() != null) {
			transporter.setKyc(updateTransporter.getKyc());
		}

		if (updateTransporter.getTransporterApproved() != null) {
			transporter.setTransporterApproved(updateTransporter.getTransporterApproved());
		}

		if (updateTransporter.getCompanyApproved() != null) {
			transporter.setCompanyApproved(updateTransporter.getCompanyApproved());
		}

		if(updateTransporter.getAccountVerificationInProgress() != null) {
			transporter.setAccountVerificationInProgress(updateTransporter.getAccountVerificationInProgress());
		}

		transporterdao.save(transporter);
		log.info("transporter is updated in the database");

		updateResponse.setTransporterId(transporter.getTransporterId());
		updateResponse.setPhoneNo(transporter.getPhoneNo());
		updateResponse.setTransporterName(transporter.getTransporterName());
		updateResponse.setTransporterLocation(transporter.getTransporterLocation());
		updateResponse.setCompanyName(transporter.getCompanyName());
		updateResponse.setEmailId(transporter.getEmailId());
		updateResponse.setKyc(transporter.getKyc());
		updateResponse.setTransporterApproved(transporter.isTransporterApproved());
		updateResponse.setCompanyApproved(transporter.isCompanyApproved());
		updateResponse.setAccountVerificationInProgress(transporter.isAccountVerificationInProgress());
		updateResponse.setStatus(CommonConstants.success);
		updateResponse.setMessage(CommonConstants.updateSuccess);
		updateResponse.setTimestamp(transporter.getTimestamp());

		log.info("updateTransporter response is returned");
		return updateResponse;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteTransporter(String transporterId) {
		log.info("deleteTransporter service is started");
		Optional<Transporter> T = transporterdao.findById(transporterId);
		if(T.isEmpty())
			throw new EntityNotFoundException(Transporter.class, "id", transporterId.toString());

		transporterdao.deleteById(transporterId);
		log.info("transporter is deleted in the database");
	}

}
