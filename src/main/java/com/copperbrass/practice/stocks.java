package com.copperbrass.practice;


import java.util.List;

import com.copperbrass.practice.purchase.purchasedetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class stocks {
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num;
	
	private String id;
	
	private String name;
	
	private String category;
	
	private String price;
	
	private String imgsrc;
	
	private String details;
	
	private Integer stock; 
	
	private String explanation; 
	
	private String mainshow;
	
	private String showorhide;
	
	@OneToMany(mappedBy = "stock")
	private List<purchasedetails> purchasedetails;
	
}
