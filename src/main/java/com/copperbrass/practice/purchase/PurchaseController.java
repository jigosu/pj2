package com.copperbrass.practice.purchase;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.copperbrass.practice.SiteUser;
import com.copperbrass.practice.StocksService;
import com.copperbrass.practice.stocks;
import com.copperbrass.practice.refund.Refund;
import com.copperbrass.practice.refund.RefundRepository;
import com.copperbrass.practice.refund.RefundService;
import com.copperbrass.practice.user.UserCreateForm;
import com.copperbrass.practice.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;


@RequiredArgsConstructor
@Controller
public class PurchaseController {

	private final PurchaseService purchaseService;
	private final PurchasedetailService purchasedetailService;
	private final StocksService stocksService;
	private final UserService userservice;
	private final RefundService refundService;
	
	private final PurchaseRepository purchaseRepository;
	private final PurchasedetailsRepository purchasedetailsRepository;
	private final RefundRepository refundRepository;
	
	
	
    @PostMapping("/purchase/purchase")
    public String purchaseItems(HttpServletRequest request, Principal principal) {
    	
    	String[] name_list = request.getParameterValues("name_list"); 	// [0]에만 값이 있음    	
    	String[] quanArray = request.getParameterValues("quan");
    	System.out.println(quanArray[0]);
    	
    	String[] stocks_names = name_list[0].split(",");
    	
    	// purchase 만들기(임시로 무조건 user가 작성했다고 판단)
    	//SiteUser user2 = userservice.findUserByName2("user");
    	
    	SiteUser user2 = userservice.findUserByName2(principal.getName());
    	System.out.println(principal.getName());
    	
    	// 주문서 만들기
    	purchase purchase_t = this.purchaseService.create(user2);
    	
    	//일괄업로드위해 만든 리스트
    	List<purchasedetails> uploadList = new ArrayList<>(); 
    	
    	float total_price = 0;
    	
    	for(int i=0;i<quanArray.length;i++) {

    		purchasedetails tmp_details = new purchasedetails();
    		stocks stock = stocksService.findByName(stocks_names[i]);
    		
    		Float count = Float.parseFloat(quanArray[i]);
    		Float price = Float.parseFloat(stock.getPrice().replace("$", ""));
    		
    		tmp_details.setStock(stock);
    		tmp_details.setPurchase(purchase_t);
    		tmp_details.setCount(Integer.parseInt(quanArray[i]));
    		tmp_details.setPrice(Float.toString(price*count));
    		
    		total_price = total_price + price*count;
    		
    		uploadList.add(tmp_details);

    	}
    	this.purchaseService.updatePrice(purchase_t, total_price);
    	this.purchasedetailService.saveall(uploadList);

    	return "redirect:/copperbrass/mypage";
    }
    
    
    
    @GetMapping("/copperbrass/mypage")
    public String MyPage(Model model, Principal principal) {
    	

    	System.out.println("purchase controller /copperbrass/mypage"); //여기가문제 여기 고치기
    	System.out.println();
    	if(principal == null) {
    		// 여기 에러
    		return "redirect:/copperbrass/shop";
    	}    	

    	
        SiteUser user = userservice.findUserByName2(principal.getName());
        if (user == null) {
            return "redirect:/copperbrass/shop";
        }

        // 최신 5개의 purchase와 관련된 purchasedetails 가져오기
        List<purchase> latestPurchases = purchaseService.findPagedPurchases(user,0);

        model.addAttribute("mypageList", latestPurchases);
        return "mypage";  	
    }    
	
 
    @PostMapping("/copperbrass/mypage/purchases")
    @ResponseBody
    public List<PurchaseDTO> getPagedPurchases(@RequestParam Map<String, Object> vo, Principal principal) {
        if (principal == null) {
            return Collections.emptyList();
        }

        SiteUser user = userservice.findUserByName2(principal.getName());
        if (user == null) {
            return Collections.emptyList();
        }

        int page = Integer.parseInt((String) vo.get("Page")); // 페이지 번호 파싱
        return purchaseService.findPagedPurchasesAsDTO(user, page); // Service 호출
    }
    

    @GetMapping("/copperbrass/mypage_details/{orderid}")
    public String nextPage(@PathVariable("orderid") String orderid, Model model, Principal principal) {
    	
    	if(principal == null) {
    		return "redirect:/copperbrass/shop";
    	}        	
    	System.out.println(orderid);
    	
    	purchase targetPurchase = purchaseService.getPurchaseidByOrderid(orderid);
	    List<purchasedetails> purchasedetails = purchasedetailService.getPurchasedetailsListByPurchaseid(targetPurchase.getId());
	    
	    
	    model.addAttribute("purchasedetails", purchasedetails);
	    model.addAttribute("targetPurchase", targetPurchase);
	    
        Refund refund = refundRepository.findByOrderid(orderid).orElse(null);
        model.addAttribute("refundhistory", refund);	 
	    
	    boolean hasRefunded = purchasedetails.stream()
	    	    .anyMatch(detail -> "Refunded".equals(detail.getStatus()));
	    
	    model.addAttribute("hasRefunded", hasRefunded);

        return "mypage-details";
    }
   
    
    @GetMapping("/copperbrass/history_config")
    public String getHistoryConfig( Model model, Principal principal) {

        // 로그인 여부 확인
        if (principal == null) {
            return "redirect:/copperbrass/shop";
        }

        int page = 0;
        
        // 한 페이지에 4개의 purchase 데이터를 가져오기
        List<purchase> adminPurchase = purchaseService.getAllPurchaseidBy4(page);

        // targetPurchase에서 ID 리스트 추출
        List<Integer> purchaseIds = adminPurchase.stream()
                                                  .map(purchase::getId)
                                                  .collect(Collectors.toList());

        // 추출한 ID 리스트를 사용해 purchasedetails 가져오기
        List<purchasedetails> purchasedetails = purchasedetailService.getDetailsByPurchaseIds(purchaseIds);
        
        System.out.println(purchasedetails);

        // 모델에 데이터 추가
        model.addAttribute("purchasedetails", purchasedetails);
        model.addAttribute("adminPurchase", adminPurchase);

        return "history-config"; // 렌더링할 HTML 파일 이름
    }

    @PostMapping("/copperbrass/history-config/purchases")
    @ResponseBody
    public List<PurchaseDTO> getHistoryConfig(@RequestParam Map<String, Object> vo, Principal principal) {
        if (principal == null) {
            return Collections.emptyList();
        }
        
        // 관리자 권한 넣기

        int page = Integer.parseInt((String) vo.get("Page")); // 페이지 번호 파싱
        return purchaseService.findPagedPurchasesAsDTO(page); // Service 호출
    }
    
    @GetMapping("/copperbrass/history_config_details/{orderid}")
    public String admin_details(@PathVariable("orderid") String orderid, Model model, Principal principal) {
    	
    	if(principal == null) {
    		return "redirect:/copperbrass/shop";
    	}        	
    	System.out.println(orderid);
    	
    	purchase targetPurchase = purchaseService.getPurchaseidByOrderid(orderid);
	    List<purchasedetails> purchasedetails = purchasedetailService.getPurchasedetailsListByPurchaseid(targetPurchase.getId());
	    model.addAttribute("purchasedetails", purchasedetails);
	    model.addAttribute("targetPurchase", targetPurchase); // Optional 내부 값만 전달

	    boolean hasRefunded = purchasedetails.stream()
	    	    .anyMatch(detail -> "Refunded".equals(detail.getStatus()));
	    
	    model.addAttribute("hasRefunded", hasRefunded);	 
	    
//	    Refund refundId = refundService.getRefundId();
        // refund 만들기
        Refund refund = refundRepository.findByOrderid(orderid).orElse(null);
        model.addAttribute("refundhistory", refund);	 


        return "history-config-details";
    }
    
    
    @PostMapping("/copperbrass/history-config-details/status")
    @ResponseBody
    public void setPurchaseStatus(@RequestParam Map<String, Object> vo, Principal principal) {
    	
    	String value = (String) vo.get("status_value");
    	
    	purchase purchase = purchaseRepository.findByOrderid((String) vo.get("orderid"));
    	
    	purchase.setDeposit(value);
    	
    	purchaseRepository.save(purchase);

    }
    
    
    @PostMapping("/copperbrass/history-config-details/refundStatus")
    @ResponseBody
    public void setRefundStatus(@RequestParam Map<String, Object> vo, Principal principal) {
    	
    	String value = (String) vo.get("status_value");
    	String orderId = (String) vo.get("orderid");
    	
    	Optional<Refund> refundOptional = refundRepository.findByOrderid(orderId);
    	
    	System.out.println(refundOptional);
    	
    	if (refundOptional.isPresent()) {
    	    Refund refund = refundOptional.get(); // Optional 내부 객체 가져오기
    	    refund.setStatus(value); // Refund 객체의 setStatus 호출
    	    refundRepository.save(refund); // 수정된 Refund 저장
    	} else {
    	    // 값이 없을 때 처리
    	    System.out.println("Refund not found for orderid: " + vo.get("orderid"));
    	}
    	
    	if (value == "3") {  		
    		purchase purchase = purchaseService.getPurchaseidByOrderid(orderId);
    		purchase.setDeposit("8");
    		purchaseRepository.save(purchase);

    	}
    }
    
    
    @GetMapping("/copperbrass/mypage_details/refund/{orderid}")
    public String refundCheck(@PathVariable("orderid") String orderid, Model model, Principal principal) {
    	
    	if(principal == null) {
    		return "redirect:/copperbrass/shop";
    	}        	
    	System.out.println(orderid);
    	
    	purchase targetPurchase = purchaseService.getPurchaseidByOrderid(orderid);
	    List<purchasedetails> purchasedetails = purchasedetailService.getPurchasedetailsListByPurchaseid(targetPurchase.getId());
	    model.addAttribute("purchasedetails", purchasedetails);
	    model.addAttribute("targetPurchase", targetPurchase); // Optional 내부 값만 전달

	    boolean hasRefunded = purchasedetails.stream()
	    	    .anyMatch(detail -> "Refunded".equals(detail.getStatus()));
	    
        Refund refund = refundRepository.findByOrderid(orderid).orElse(null);
        model.addAttribute("refundhistory", refund);		    
	    
	    model.addAttribute("hasRefunded", hasRefunded);	    

        return "mypage-refund";
    }
    

    @PostMapping("/copperbrass/history-config-details/process-refund")
    @ResponseBody
    public void processRefund(@RequestBody Map<String, Object> payload) {
    	// selectedIds, purchaseId, orderId 가져오기
        List<String> selectedIds = (List<String>) payload.get("selectedIds");
        String purchaseIdString  = (String) payload.get("purchaseId");
        String orderId  = (String) payload.get("orderId");

        if (selectedIds == null || purchaseIdString == null || orderId == null) {
            System.out.println("Invalid payload: Missing selectedIds or purchaseId");
            return;
        }
        
        // purchaseId를 Integer로 변환
        Integer purchaseId;
        try {
            purchaseId = Integer.parseInt(purchaseIdString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid purchaseId format: " + purchaseIdString);
            return;
        }        
        
        
        // refund 만들기
        Refund refund = refundRepository.findByOrderid(orderId).orElse(null);
        if (refund == null) {
            // 기존 Refund가 없으면 새로 생성
            refund = new Refund();
            refund.setOrderid(orderId); 
        }
        refund.setRefunddatetime(LocalDateTime.now());
        refund.setStatus("1"); // 환불 신청 상태
        refundRepository.save(refund);
        
        
        
        List<Integer> checkedIds = new ArrayList<>();
        List<Integer> uncheckedIds = new ArrayList<>();

        for (String id : selectedIds) {
            if (id.startsWith("unchecked-")) {
                uncheckedIds.add(Integer.parseInt(id.replace("unchecked-", "")));
            } else {
                checkedIds.add(Integer.parseInt(id));
            } 
        }

        System.out.println("Checked IDs: " + checkedIds);
        System.out.println("Unchecked IDs: " + uncheckedIds);
        System.out.println("Purchase ID: " + purchaseId);
        
        List<purchasedetails> refund_purchasedetails = purchasedetailService.getDetailsByPurchaseIdAndIds(purchaseId, checkedIds);
        List<purchasedetails> unrefund_purchasedetails = purchasedetailService.getDetailsByPurchaseIdAndIds(purchaseId, uncheckedIds);

        
        for (purchasedetails detail : refund_purchasedetails) {
            detail.setStatus("Refunded"); 
            detail.setStatusdatetime(LocalDateTime.now()); 
            detail.setRefund(refund);
            purchasedetailsRepository.save(detail);
        }        
        
        for (purchasedetails detail : unrefund_purchasedetails) {
            detail.setStatus(""); 
            detail.setStatusdatetime(LocalDateTime.now());
            detail.setRefund(null); // Refund 초기화
            purchasedetailsRepository.save(detail); 
        }
        
        // 환불을 고객이 취소했을 때 -> refund 환불 전으로 돌리고 purchase도 배송완료로 변경
        if(refund_purchasedetails.isEmpty()&& refund != null) {
        	refund.setStatus("0");
        	refundRepository.save(refund);
        	
        	purchase purchase = purchaseService.getPurchaseidByOrderid(orderId);
        	purchase.setDeposit("4"); 
        	purchaseRepository.save(purchase);
        	
        }else if(!refund_purchasedetails.isEmpty()) {
        	purchase purchase = purchaseService.getPurchaseidByOrderid(orderId);
        	purchase.setDeposit("5"); 
        	purchaseRepository.save(purchase);       	
        }

        
    }


    @PostMapping("/copperbrass/history-config-details/process-confirm")
    @ResponseBody
    public void processConfirm(@RequestBody Map<String, Object> payload) {
    	// selectedIds, purchaseId, orderId 가져오기
        String purchaseIdString  = (String) payload.get("purchaseId");
        String orderId  = (String) payload.get("orderId");

        if ( purchaseIdString == null || orderId == null) {
            System.out.println("Invalid payload: Missing selectedIds or purchaseId");
            return;
        }
        
        System.out.println(orderId);
        
        // purchaseId를 Integer로 변환
        Integer purchaseId;
        try {
            purchaseId = Integer.parseInt(purchaseIdString);
        } catch (NumberFormatException e) {
            System.out.println("Invalid purchaseId format: " + purchaseIdString);
            return;
        }        
        String deposit = "9";
        purchase purchase = purchaseService.getPurchaseidByOrderid(orderId);
        System.out.println(purchase.getId());
        purchasedetailService.updateStatusByPurchaseId(purchase.getId());
        purchaseService.updateDeposit(orderId,deposit);
            
        
    }    
    
    
	public void AuthRole(Principal principal, Model model) {
		if(principal != null) {
			model.addAttribute("principal",principal);
		}	
	}    
	

}
