package com.copperbrass.practice;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.Principal;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {

	final private StocksService stocksService;
	

	
	@GetMapping("/copperbrass")
	public String hello(Model model, Principal principal) {
		
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
		
		AuthRole(principal,model);
		
		
		return "main";
	}
	
	@GetMapping("/copperbrass/about")
	public String aboutPage(Model model) {
		
		
		return "about";
	}
	
	@GetMapping("/copperbrass/shop")
	public String aboutShop(Model model, @RequestParam(value="num", defaultValue="") String category,Principal principal) {
		
		List<stocks> stocks = new ArrayList<>();
		int lastStockNum = 0;
		
		if(category.isBlank()) {
			stocks = stocksService.find4ByNum();
		}else {
			stocks = stocksService.find4ByNum(category);
		}
		
		model.addAttribute("firstStocks",stocks);
		
		if (!stocks.isEmpty()) {
			lastStockNum = stocks.get(stocks.size() - 1).getNum();
		}
		
		model.addAttribute("lastStockNum",lastStockNum);
		
		AuthRole(principal,model);
		
		return "shop";
	}	
		

	
	@GetMapping("/copperbrass/findAllProductList")
	@ResponseBody
	public List<stocks> findAllProductList(){
		List<stocks> addstocks = new ArrayList<>();
		
		//addstocks = this.stocksService.find4ByNum(6);

		
		return addstocks;// @ResponseBody에 의해 JSONArray 로 응답한다 
	}	
	
	@PostMapping("/copperbrass/findAllProductList")
	@ResponseBody
	public List<stocks> findAllProductListP(@RequestParam Map<String, Object> vo){
		
		List<stocks> addstocks = new ArrayList<>();
		int lastStockNum = Integer.parseInt((String) vo.get("lastStockNum"));
		String category = (String) vo.get("category");
		
		
		if (category.isBlank()) {
			addstocks = this.stocksService.find4ByNum(lastStockNum);
		}else {
			addstocks = this.stocksService.find4ByNum(category,lastStockNum);
		}
		
		return addstocks;// @ResponseBody에 의해 JSONArray 로 응답한다 
	}	
	
	@GetMapping("/copperbrass/shop-details/{number}")
	public String stockDetails(Model model, @PathVariable("number") int number,Principal principal){
		stocks stock = new stocks();
		
		stock = this.stocksService.findByNum(number);
		model.addAttribute("stock",stock);
		
		AuthRole(principal,model);
		
		return "shop-details";// @ResponseBody에 의해 JSONArray 로 응답한다 
	}	
	
	@GetMapping("/copperbrass/contact")
	public String aboutContact(Model model) {
		

		return "contact";
	}	
	/*
	 * @GetMapping("/copperbrass/contact") 
	 * public String aboutContact(Model model) {
	 * List<stocks> stocks = this.stocksService.findAll();
	 * 
	 * model.addAttribute("stocks",stocks);
	 * 
	 * return "contact"; }
	 */		
	
	@GetMapping("/copperbrass/policies-shipping")
	public String aboutPoiciesShipping(Model model) {
		

		return "policies-shipping";
	}	
	
	@GetMapping("/copperbrass/view-cart")
	public String aboutViewCart(Model model) {
		

		return "view-cart";
	}	
	
	@GetMapping("/copperbrass/shop-register")
	public String registerItems(Model model, @RequestParam(value="name", defaultValue="") String name) {
		
		stocks stock = new stocks();
		
		if(!name.isBlank()) {
			stock = stocksService.findByName(name);
		}	
		
		model.addAttribute("updatestocks",stock);
		
		return "shop_register";
	}	
	
	@GetMapping("/copperbrass/shop-register2")
	public String registerItems2(Model model) {
		
		stocks stock = new stocks();
		
		model.addAttribute("updatestocks",stock);
		model.addAttribute("duplicate", "같은 내용의 상품이 있습니다.");
		model.addAttribute("searchUrl", "/copperbrass/shop-register");		
		
		return "shop_register";
	}		
	
	public void AuthRole(Principal principal, Model model) {
		if(principal != null) {
			model.addAttribute("principal",principal);
		}	
	}
	
	
	
	
    @PostMapping(value="/copperbrass/upload")
	public String upload( Model model,HttpServletRequest request) throws IOException {
    	
    	String title = request.getParameter("title");
    	String nextPage = null;
    	if(this.stocksService.checkIdDuplicate(title)) {
    		System.out.println("중복됨");
    		
    		nextPage = "redirect:/copperbrass/shop-register2";

    	}else {
    	
	    	System.out.println("중복안됨");
			// 처음 등록할 때
	    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    	MultipartFile file = multipartRequest.getFile("file");
	    	
			System.out.println("파일 이름 : " + file.getOriginalFilename());
			System.out.println("파일 크기 : " + file.getSize());
	  
	        
	        try(
	                // 맥일 경우 
	                //FileOutputStream fos = new FileOutputStream("/tmp/" + file.getOriginalFilename());
	                // 윈도우일 경우
	                FileOutputStream fos = new FileOutputStream("C:/Users/jyjang/git/repository/CopperBrass/src/main/resources/static/img/main/bestseller/" + file.getOriginalFilename());
	                InputStream is = file.getInputStream();
	        ){
	        	    int readCount = 0;
	        	    byte[] buffer = new byte[1024];
	            while((readCount = is.read(buffer)) != -1){
	                fos.write(buffer,0,readCount);
	            }
	        }catch(Exception ex){
	            throw new RuntimeException("file Save Error");
	        }
	        
	        String src = "/img/main/bestseller/"+file.getOriginalFilename();
	        
	        String price = request.getParameter("price");
	        if(price.startsWith("$")) {
	        	price = price.replace("$", "");
	        }
	        
	        
	        String category = request.getParameter("category");
	        String explanation = request.getParameter("explanation");

        stocksService.registerItem(title,category,price,explanation,src);
//Insert into stocks(id,name,category,price,imgsrc) values ('main4_4','Goal Getter Sticky Notes','Sticky Notes','$3.00','/img/main/bestseller/main4_4.JPG');
        nextPage = "redirect:/copperbrass/shop";
    	}
    	return nextPage;
		
	}    	
	
    
    // update 할 때 
    @PostMapping(value="/copperbrass/update")
	public String update(HttpServletRequest request) {
        
        String src = request.getParameter("file");

        String price = request.getParameter("price");
        if(price.startsWith("$")) {
        	price = price.replace("$", "");
        }
        
        int numId = Integer.parseInt(request.getParameter("numId"));
        String title = request.getParameter("title");
        String category = request.getParameter("category");
        String explanation = request.getParameter("explanation");
        

//Insert into stocks(id,name,category,price,imgsrc) values ('main4_4','Goal Getter Sticky Notes','Sticky Notes','$3.00','/img/main/bestseller/main4_4.JPG');
        stocksService.updateItem(numId,title,category,price,explanation);
        
		return "redirect:/copperbrass/shop";
	}       
    
    
    // delete 할 때 
    @PostMapping(value="/copperbrass/delete")
	public String delete(HttpServletRequest request) {
        
        int targetnum = Integer.parseInt(request.getParameter("targetnum"));
        System.out.println(targetnum);
        stocksService.deleteItem(targetnum);
        
		return "redirect:/copperbrass/shop";
	}       
    

    

}
