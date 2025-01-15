package com.copperbrass.practice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminController {

	final private StocksService stocksService;
	
	@GetMapping("/copperbrass/admin")
	public String hello(Model model) {
		
		List<stocks> bestseller1 = new ArrayList<>();
		bestseller1 = this.stocksService.mainBestseller1();
		
		List<stocks> bestseller2 = new ArrayList<>();
		bestseller2 = this.stocksService.mainBestseller("main2");
		
		List<stocks> bestseller3 = new ArrayList<>();
		bestseller3 = this.stocksService.mainBestseller("main3");
		
		List<stocks> bestseller4 = new ArrayList<>();
		bestseller4 = this.stocksService.mainBestseller("main4");
		
		
		model.addAttribute("bestseller1",bestseller1);
		model.addAttribute("bestseller2",bestseller2);
		model.addAttribute("bestseller3",bestseller3);
		model.addAttribute("bestseller4",bestseller4);		
		model.addAttribute("test","/img/main/bestseller/main_bundle.JPG");
		
		return "main";
	}
	

	

}
