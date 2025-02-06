package com.copperbrass.practice.purchase;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.copperbrass.practice.SiteUser;

public interface PurchaseRepository extends JpaRepository<purchase, Integer> {
	
	@Query("SELECT p FROM purchase p WHERE p.user=?1 ")
	List<purchase> findDeposit1status(SiteUser user);
	
	purchase findByOrderid(String orderid);
	
	Optional<purchase> findById(Integer id);
	
    @EntityGraph(attributePaths = "purchaseDetailsList")
    Page<purchase> findByUser(SiteUser user, Pageable pageable);

    @EntityGraph(attributePaths = "purchaseDetailsList")
    Page<purchase> findAll(Pageable pageable);    
    
    @Query("SELECT p FROM purchase p JOIN FETCH p.purchaseDetailsList WHERE p.user = :user")
    List<purchase> findPurchasesWithDetails(@Param("user") SiteUser user);
    
    @Modifying
    @Transactional
    @Query("UPDATE purchase p SET p.deposit = :deposit WHERE orderid = :orderid ")
    void updateDepositByOrderid(@Param("orderid") String orderId,@Param("deposit") String deposit);
}
