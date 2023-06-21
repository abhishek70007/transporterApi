package com.springboot.TransporterAPI.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.ShipperAPI.Entity.Shipper;
import com.springboot.TransporterAPI.Entity.Transporter;

@Repository
public interface TransporterDao extends JpaRepository<Transporter, String>  {
	@Query("select t from Transporter t")
	public List<Transporter> getAll(Pageable pageable);

	@Query("select t from Transporter t where t.phoneNo = :phoneNo")
	public Optional<Transporter> findByPhoneNo(String phoneNo);
	
	@Query("select t from Transporter t where t.emailId = :emailId")
	public Optional<Transporter> findByEmailId(String emailId);

	@Query("select t from Transporter t where t.transporterApproved = :transporterApproved")
	public List<Transporter> findByTransporterApproved(Boolean transporterApproved, Pageable pageable);

	@Query("select t from Transporter t where t.companyApproved = :companyApproved")
	public List<Transporter> findByCompanyApproved(Boolean companyApproved, Pageable pageable);

	@Query("select t from Transporter t where t.transporterApproved = :transporterApproved AND t.companyApproved = :companyApproved")
	public List<Transporter> findByTransporterCompanyApproved(Boolean transporterApproved, Boolean companyApproved, Pageable pageable);

}
