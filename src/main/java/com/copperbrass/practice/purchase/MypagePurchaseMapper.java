package com.copperbrass.practice.purchase;

import java.util.stream.Collectors;


public class MypagePurchaseMapper {

    public static PurchaseDTO toDTO(purchase purchase) {
        return new PurchaseDTO(
            purchase.getId(),
            purchase.getOrderid(),
            purchase.getOrderdatetime().toString(),
            purchase.getDeposit(),
            purchase.getTotalprice(),
            purchase.getPurchaseDetailsList().stream()
                .map(detail -> new PurchaseDetailsDTO(
                    detail.getStock().getName(),
                    detail.getCount(),
                    detail.getPrice(),
                    detail.getStock().getImgsrc() 
                ))
                .collect(Collectors.toList()),
            new UserDTO(
                    purchase.getUser().getId(),
                    purchase.getUser().getUsername()
                )
             
        );
        
    }
}
