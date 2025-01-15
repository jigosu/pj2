package com.copperbrass.practice.refund;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Integer>{
	
	Optional<Refund> findByOrderid(String orderid);
	

}