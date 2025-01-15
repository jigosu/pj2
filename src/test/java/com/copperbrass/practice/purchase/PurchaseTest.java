package com.copperbrass.practice.purchase;

import com.copperbrass.practice.SiteUser;
import com.copperbrass.practice.purchase.purchase;
import com.copperbrass.practice.purchase.purchasedetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseTest {

    @Test
    public void testPurchaseSerialization() throws Exception {
        // 객체 생성
        purchase p = new purchase();
        p.setId(1);
        p.setOrderid("ORD123");
        p.setOrderdatetime(LocalDateTime.now());
        p.setDeposit("1");
        p.setTotalprice("10000");

        // purchasedetails 객체 생성
        purchasedetails details1 = new purchasedetails();
        details1.setId(1);
        details1.setCount(2);
        details1.setPrice("5000");
        details1.setPurchase(p);

        purchasedetails details2 = new purchasedetails();
        details2.setId(2);
        details2.setCount(3);
        details2.setPrice("3000");
        details2.setPurchase(p);

        p.setPurchaseDetailsList(List.of(details1, details2));

        // JSON 직렬화
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(p);

        // 결과 출력
        System.out.println(json);
    }
}
