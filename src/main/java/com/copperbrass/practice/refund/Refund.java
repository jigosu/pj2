package com.copperbrass.practice.refund;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import com.copperbrass.practice.SiteUser;
import com.copperbrass.practice.purchase.purchasedetails;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;


@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@Entity
public class Refund {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	private String orderid;
	
	private LocalDateTime  refunddatetime;
	
	// 1:환불신청 2:환불확인(관리자) 3:환불완료
	private String status; 

	private String totalprice;
	
	@OneToMany(mappedBy = "refund", cascade = CascadeType.ALL)
	private List<purchasedetails> purchaseDetailsList;

	private  String userid;


	
}
