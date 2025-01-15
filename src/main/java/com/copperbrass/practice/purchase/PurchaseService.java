package com.copperbrass.practice.purchase;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.copperbrass.practice.SiteUser;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PurchaseService {
	
	private final PurchaseRepository purchaseRepository;
	
	public purchase create(SiteUser user ) {
		purchase p = new purchase();
		
		// 주문서 이름 만들어야함
		p.setOrderid(user.getUsername()+LocalDateTime.now());
		p.setOrderdatetime(LocalDateTime.now());
		p.setDeposit("0");
		p.setUser(user);	
		
		return this.purchaseRepository.save(p);
		
	}
	
	public void updatePrice(purchase p, Float price) {
		p.setTotalprice(Float.toString(price));
		this.purchaseRepository.save(p);
	}
	
//	public List<purchase> findDeposit1status(SiteUser user){
////		return this.purchaseRepository.findDeposit1status(user);
//		return purchaseRepository.findByUser(user);
//	}
	
	public List<purchase> findPagedPurchases(SiteUser user, int page) {
	    int pageSize = 2; // 페이지 당 항목 수
	    Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
	    return purchaseRepository.findByUser(user, pageable).getContent();
	
	}
	public List<purchase> findPagedPurchases(int page) {
	    int pageSize = 2; // 페이지 당 항목 수
	    Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));
	    return purchaseRepository.findAll(pageable).getContent();
	
	}	
		
	
	
	public Optional<purchase> getPurchasedetails(Integer id){
		return this.purchaseRepository.findById(id);
	}
	
	public purchase getPurchaseidByOrderid(String orderid){
		return this.purchaseRepository.findByOrderid(orderid);
	} 
	
	public List<purchase> getAllPurchaseidBy4(int page){
		int pageSize = 2;
		Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "orderdatetime"));
	    return this.purchaseRepository.findAll(pageable).getContent();
	} 	
	
    // DTO 변환을 위한 메서드 추가
    public List<PurchaseDTO> findPagedPurchasesAsDTO(SiteUser user, int page) {
        List<purchase> purchases = this.findPagedPurchases(user, page); // 엔티티 가져오기
        return purchases.stream()
            .map(MypagePurchaseMapper::toDTO) // Mapper를 사용하여 변환
            .collect(Collectors.toList());
    }	
    
    // DTO 변환을 위한 메서드 추가
    public List<PurchaseDTO> findPagedPurchasesAsDTO(int page) {
        List<purchase> purchases = this.findPagedPurchases(page); // 엔티티 가져오기
        return purchases.stream()
            .map(MypagePurchaseMapper::toDTO) // Mapper를 사용하여 변환
            .collect(Collectors.toList());
    }	    

}
