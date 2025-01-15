package com.copperbrass.practice.purchase;

public class PurchaseDetailsDTO {
    private String stockName;
    private int count;
    private String price;
    private String imgsrc;

    // 생성자
    public PurchaseDetailsDTO(String stockName, int count, String price, String imgsrc) {
        this.stockName = stockName;
        this.count = count;
        this.price = price;
        this.imgsrc = imgsrc;
    }

    // Getter/Setter
    public String getStockName() { return stockName; }
    public void setStockName(String stockName) { this.stockName = stockName; }
    public int getCount() { return count; }
    public void setCount(int count) { this.count = count; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }    
    public String getImgsrc() { return imgsrc; }
    public void setImgsrc(String imgsrc) { this.imgsrc = imgsrc; }    
}
