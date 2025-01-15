package com.copperbrass.practice.purchase;

import java.util.List;

public class PurchaseDTO {
    private Integer id;
    private String orderId;
    private String orderDateTime;
    private String deposit; 
    private String totalprice;    
    private List<PurchaseDetailsDTO> purchaseDetailsList;
    private UserDTO user; // User 정보 추가

    // 생성자
    public PurchaseDTO(Integer id, String orderId, String orderDateTime, String deposit, String totalprice, List<PurchaseDetailsDTO> purchaseDetailsList, UserDTO user) {
        this.id = id;
        this.orderId = orderId;
        this.orderDateTime = orderDateTime;
        this.purchaseDetailsList = purchaseDetailsList;
        this.deposit = deposit;
        this.totalprice = totalprice;
        this.user = user; // User 추가
    }

    // Getter/Setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getOrderDateTime() { return orderDateTime; }
    public void setOrderDateTime(String orderDateTime) { this.orderDateTime = orderDateTime; }
    public String getTotalPrice() { return totalprice; }
    public void setTotalPrice(String totalprice) { this.totalprice = totalprice; }
    public String getDeposit() { return deposit; }
    public void setDeposit(String deposit) { this.deposit = deposit; }    
    public List<PurchaseDetailsDTO> getPurchaseDetailsList() { return purchaseDetailsList; }
    public void setPurchaseDetailsList(List<PurchaseDetailsDTO> purchaseDetailsList) { this.purchaseDetailsList = purchaseDetailsList; }
    public UserDTO getUser() { return user; } // User Getter
    public void setUser(UserDTO user) { this.user = user; } // User Setter
}
