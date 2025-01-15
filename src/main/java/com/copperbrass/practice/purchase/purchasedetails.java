package com.copperbrass.practice.purchase;

import java.time.LocalDateTime;
import java.util.List;

import com.copperbrass.practice.stocks;
import com.copperbrass.practice.refund.Refund;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
public class purchasedetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		
	private Integer id;

	@ManyToOne
	private stocks stock;
	
	@ManyToOne
	private purchase purchase;
	
	private Integer count;
	
	private String price;
	
	private String status;
	
	private LocalDateTime  statusdatetime;

	@ManyToOne
	@JoinColumn(name = "refund_id")
	private Refund refund;
	
}
