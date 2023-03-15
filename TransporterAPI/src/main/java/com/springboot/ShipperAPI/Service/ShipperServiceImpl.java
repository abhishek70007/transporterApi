package com.springboot.ShipperAPI.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.apache.commons.lang3.StringUtils;

import com.springboot.ShipperAPI.Constants.CommonConstants;
import com.springboot.ShipperAPI.Dao.ShipperDao;
import com.springboot.ShipperAPI.Entity.Shipper;
import com.springboot.ShipperAPI.Model.PostShipper;
import com.springboot.ShipperAPI.Model.UpdateShipper;
import com.springboot.ShipperAPI.Response.ShipperCreateResponse;
import com.springboot.ShipperAPI.Response.ShipperUpdateResponse;

import lombok.extern.slf4j.Slf4j;

import com.springboot.TransporterAPI.Exception.BusinessException;
import com.springboot.TransporterAPI.Exception.EntityNotFoundException;

@Slf4j
@Service
public class ShipperServiceImpl implements ShipperService {

	@Autowired
	ShipperDao shipperdao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ShipperCreateResponse addShipper(PostShipper postshipper) {
		log.info("addShipper service is started");

		String temp="";
		Shipper shipper = new Shipper();
		ShipperCreateResponse response = new ShipperCreateResponse();

		Optional<Shipper> s = shipperdao.findShipperByPhoneNo(postshipper.getPhoneNo());
		if (s.isPresent()) {
			response.setShipperId(s.get().getShipperId());
			response.setPhoneNo(s.get().getPhoneNo());
			response.setShipperName(s.get().getShipperName());
			response.setShipperLocation(s.get().getShipperLocation());
			response.setCompanyName(s.get().getCompanyName());
			response.setKyc(s.get().getKyc());
			response.setCompanyApproved(s.get().isCompanyApproved());
			response.setAccountVerificationInProgress(s.get().isAccountVerificationInProgress());
			response.setMessage(CommonConstants.ACCOUNT_EXIST);
			response.setTimestamp(s.get().getTimestamp());
			return response;
		}

		temp="shipper:"+UUID.randomUUID();
		shipper.setShipperId(temp);
		response.setShipperId(temp);

		temp=postshipper.getShipperName();
		if (StringUtils.isNotBlank(temp)) {
			shipper.setShipperName(temp.trim());
			response.setShipperName(temp.trim());
		}

		temp=postshipper.getCompanyName();
		if(StringUtils.isNotBlank(temp)) {
			shipper.setCompanyName(temp.trim());
			response.setCompanyName(temp.trim());
		}

		temp=postshipper.getShipperLocation();
		if(StringUtils.isNotBlank(temp)) {
			shipper.setShipperLocation(temp.trim());
			response.setShipperLocation(temp.trim());
		}

		temp=postshipper.getPhoneNo();
		shipper.setPhoneNo(temp);
		response.setPhoneNo(temp);

		temp=postshipper.getKyc();
		if(StringUtils.isNotBlank(temp)) {
			shipper.setKyc(temp.trim());
			response.setKyc(temp.trim());
		}

		shipper.setCompanyApproved(false);
		response.setCompanyApproved(false);

		shipper.setAccountVerificationInProgress(false);
		response.setAccountVerificationInProgress(false);

		shipperdao.save(shipper);
		log.info("shipper is saved to the database");

		response.setStatus(CommonConstants.PENDING);
		response.setMessage(CommonConstants.APPROVE_REQUEST);
		response.setTimestamp(shipper.getTimestamp());

		log.info("addShipper response is returned");
		return response;

	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public List<Shipper> getShippers(Boolean companyApproved, String phoneNo, Integer pageNo) { 
		log.info("getShippers service started");
		if(pageNo == null) {
			pageNo = 0;
		}
		Pageable page = PageRequest.of(pageNo, 15,  Sort.Direction.DESC, "timestamp");
		
		if(phoneNo != null) {
			String validate = "^[6-9]\\d{9}$";
			Pattern pattern = Pattern.compile(validate);
			Matcher m = pattern.matcher(phoneNo);
			if(m.matches()) {
				if(shipperdao.findShipperByPhoneNo(phoneNo).isPresent()) {
					List<Shipper> list = List.of(shipperdao.findShipperByPhoneNo(phoneNo).get());
					return list;
				}
				else {
					throw new EntityNotFoundException(Shipper.class, "phoneNo", phoneNo.toString());
				}
			}
			else {
				// MethodArgumentNotValidException
				throw new BusinessException("Invalid mobile number");
			}
			
		}

		if(companyApproved == null) {
			List<Shipper> shipperList = shipperdao.getAll(page);
			//Collections.reverse(shipperList);
			return shipperList;
		} 
		List<Shipper> shipperList = shipperdao.findByCompanyApproved(companyApproved, page);
		//Collections.reverse(shipperList);
		log.info("getShippers response returned");
		return shipperList;
	}

	@Transactional(readOnly = true, rollbackFor = Exception.class)
	@Override
	public Shipper getOneShipper(String shipperId) {
		log.info("getOneShipper service is started");
		Optional<Shipper> S = shipperdao.findById(shipperId);
		if(S.isEmpty()) {
			throw new EntityNotFoundException(Shipper.class, "id",shipperId.toString());
		}

		log.info("getOneShiper response is returned");
		return S.get();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public ShipperUpdateResponse updateShipper(String shipperId, UpdateShipper updateShipper) {
		log.info("updateShipper service is started");
		ShipperUpdateResponse updateResponse = new ShipperUpdateResponse();
		Shipper shipper = new Shipper();
		Optional<Shipper> S = shipperdao.findById(shipperId);

		if(S.isEmpty()) {
			throw new EntityNotFoundException(Shipper.class, "id",shipperId.toString());
		}

		String temp="";
		shipper = S.get();

		if (updateShipper.getPhoneNo() != null) {			
			throw new BusinessException("Phone no. can't be updated");
		}

		temp=updateShipper.getShipperName();
		if(StringUtils.isNotBlank(temp)) {
			shipper.setShipperName(temp.trim());
		}
		
		temp=updateShipper.getCompanyName();
		if(StringUtils.isNotBlank(temp)) {
			shipper.setCompanyName(temp.trim());
		}

		temp=updateShipper.getShipperLocation();
		if(StringUtils.isNotBlank(temp)) {
			shipper.setShipperLocation(temp.trim());
		}

		if (updateShipper.getKyc() != null) {
			shipper.setKyc(updateShipper.getKyc());
		}

		if(updateShipper.getCompanyApproved() != null) {
			shipper.setCompanyApproved(updateShipper.getCompanyApproved());
		}

		if(updateShipper.getAccountVerificationInProgress() != null) {
			shipper.setAccountVerificationInProgress(updateShipper.getAccountVerificationInProgress());
		}

		shipperdao.save(shipper);
		log.info("shipper is upadated and saved to the database");

		updateResponse.setShipperId(shipper.getShipperId());
		updateResponse.setPhoneNo(shipper.getPhoneNo());
		updateResponse.setShipperName(shipper.getShipperName());
		updateResponse.setCompanyName(shipper.getCompanyName());
		updateResponse.setShipperLocation(shipper.getShipperLocation());
		updateResponse.setKyc(shipper.getKyc());
		updateResponse.setCompanyApproved(shipper.isCompanyApproved());
		updateResponse.setAccountVerificationInProgress(shipper.isAccountVerificationInProgress());
		updateResponse.setStatus(CommonConstants.SUCCESS);
		updateResponse.setMessage(CommonConstants.UPDATE_SUCCESS);
		updateResponse.setTimestamp(shipper.getTimestamp());

		log.info("updateShipper response is returned");
		return updateResponse;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteShipper(String shipperId) {
		log.info("deleteShipper service is started");
		Optional<Shipper> S = shipperdao.findById(shipperId);

		if( S.isEmpty()) {
			throw new EntityNotFoundException(Shipper.class, "id",shipperId.toString());
		}
		shipperdao.delete(S.get());
		log.info("shipper is deleted in the database");
	}

}
