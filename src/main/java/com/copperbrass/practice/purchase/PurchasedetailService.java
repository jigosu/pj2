package com.copperbrass.practice.purchase;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;



@AllArgsConstructor
@Service
public class PurchasedetailService {
	
	private final PurchasedetailsRepository purchasedetailsRepository;

	public void saveall(List<purchasedetails> uploadList) {
		this.purchasedetailsRepository.saveAll(uploadList);
		
	}

	public List<purchasedetails> getPurchasedetailsListByPurchaseid(Integer id) {
		return this.purchasedetailsRepository.findAllByPurchaseId(id);
		
	}
	
    // 특정 purchase ID 리스트를 사용해 purchasedetails 조회
    public List<purchasedetails> getDetailsByPurchaseIds(List<Integer> purchaseIds) {
        return purchasedetailsRepository.findByPurchaseIdIn(purchaseIds);
    }	
    

    public List<purchasedetails> getDetailsByPurchaseIdAndIds(Integer purchaseId, List<Integer> ids) {
        return purchasedetailsRepository.findByPurchaseIdAndIdIn(purchaseId, ids);
    }
	   
    public void updateStatusByPurchaseId(String orderId) {
        purchasedetailsRepository.updateStatusToCByPurchaseIdAndStatusNotRefunded(orderId);
    }


    
}
