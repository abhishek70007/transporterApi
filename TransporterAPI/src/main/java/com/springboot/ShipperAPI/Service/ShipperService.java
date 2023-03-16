package com.springboot.ShipperAPI.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springboot.ShipperAPI.Entity.Shipper;
import com.springboot.ShipperAPI.Model.PostShipper;
import com.springboot.ShipperAPI.Model.UpdateShipper;
import com.springboot.ShipperAPI.Response.ShipperCreateResponse;
import com.springboot.ShipperAPI.Response.ShipperUpdateResponse;

public interface ShipperService {

	public ShipperCreateResponse addShipper(PostShipper shipper);

	public List<Shipper> getShippers(Boolean companyApproved, String phoneNo, Integer pageNo);

	public Shipper getOneShipper(String shipperId);

	public ShipperUpdateResponse updateShipper(String shipperId, UpdateShipper shipper);

	public void deleteShipper(String shipperId);


}
