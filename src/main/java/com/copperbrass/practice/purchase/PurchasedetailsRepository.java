package com.copperbrass.practice.purchase;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PurchasedetailsRepository extends JpaRepository<purchasedetails, Integer>{

	List<purchasedetails> findAllByPurchaseId(Integer id);
	
	List<purchasedetails> findByPurchaseIdIn(List<Integer> purchaseIds);
	
	List<purchasedetails> findByPurchaseIdAndIdIn(Integer purchaseId, List<Integer> ids);
	
	@Modifying
	@Transactional
	@Query("UPDATE purchasedetails p SET p.status = 'Confirmed' WHERE p.purchase.id = :purchaseId AND (p.status <> 'Refunded' OR p.status IS NULL)")
	void updateStatusToCByPurchaseIdAndStatusNotRefunded(@Param("purchaseId") Integer purchaseId);



}
