package com.copperbrass.practice.purchase;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.copperbrass.practice.SiteUser;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@Entity
public class purchase {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	private String orderid;
	
	private LocalDateTime  orderdatetime;
	
	// 0:입금대기 1:입금확인 2:출고준비 3:배송중 4:배송완료 5:환불진행 8:환불완료 9:구매확정
	private String deposit; 

	private String totalprice;
	
	@OneToMany(mappedBy="purchase", cascade = CascadeType.REMOVE)
	private List<purchasedetails> purchaseDetailsList;

	@ManyToOne
	private  SiteUser user;
	
    @Override
    public String toString() {
        return "Purchase{" +
               "id=" + id +
               ", orderId='" + orderid + '\'' +
               ", totalPrice=" + totalprice +
               '}';
    }	
}
