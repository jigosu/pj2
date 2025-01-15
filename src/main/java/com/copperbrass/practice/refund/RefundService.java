package com.copperbrass.practice.refund;

import org.springframework.stereotype.Service;

import com.copperbrass.practice.purchase.PurchaseRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RefundService{
	
	private final RefundRepository refundRepository;

	
}

