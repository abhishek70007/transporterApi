package com.springboot.ShipperAPI.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.springboot.ShipperAPI.Entity.Shipper;

public interface ShipperDao extends JpaRepository<Shipper, String> {
	@Query("select phoneNo from Shipper s where s.phoneNo = :phoneNo")
	public Long findByPhoneNo(String phoneNo);	

	@Query("select s from Shipper s where s.phoneNo = :phoneNo")
	public Optional<Shipper> findShipperByPhoneNo(String phoneNo);	

	@Query("select s from Shipper s")
	public List<Shipper> getAll(Pageable pageable);

	public List<Shipper> findByCompanyApproved(Boolean companyApproved, Pageable pageable);
}
