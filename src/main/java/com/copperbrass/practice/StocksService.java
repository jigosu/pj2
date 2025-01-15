package com.copperbrass.practice;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class StocksService {

	private final StocksRepository stocksRepository;
	
	public List<stocks> mainBestseller1() {
		return this.stocksRepository.findMainBestseller1();
	}
	
	public List<stocks> mainBestseller(String id) {
		return this.stocksRepository.findByMainshowStartsWith(id);
	}	
	
	public List<stocks> findAll(){
		return this.stocksRepository.findAll();
	}
	
	public List<stocks> find4ByNum(int num){
		return this.stocksRepository.find4stocksByNum(num);
	}
	public List<stocks> find4ByNum(){
		return this.stocksRepository.find4stocksByNum();
	}
	
	public List<stocks> find4ByNum(String category){
		return this.stocksRepository.find4stocksByNum(category);
	}
	public List<stocks> find4ByNum(String category, int num){
		return this.stocksRepository.find4stocksByNum(category, num);
	}
		
	public stocks findByNum(int num) {
		return this.stocksRepository.findByNum(num);
	}
	
	public stocks findByName(String name) {
		return this.stocksRepository.findByName(name);
	}	
	
	public void registerItem(String title, String category, String price, String explanation, String src) {
		stocks tmp_stock = new stocks();
		
		tmp_stock.setId(title);
		tmp_stock.setName(title);
		tmp_stock.setCategory(category);
		tmp_stock.setPrice("$"+price);
		tmp_stock.setExplanation(explanation);
		tmp_stock.setImgsrc(src);
		tmp_stock.setShoworhide("S");
		this.stocksRepository.save(tmp_stock);
			
	}
	
	@Transactional
	public void updateItem(int num, String title, String category, String price, String explanation) {
		stocks update_stock = stocksRepository.findByNum(num);
						
		update_stock.setId(title);
		update_stock.setName(title);
		update_stock.setCategory(category);
		update_stock.setPrice("$"+price);
		update_stock.setExplanation(explanation);
		
	
	}	
	
	@Transactional
	public void deleteItem(int num) {
		stocks hide_stock = stocksRepository.findByNum(num);

		
		hide_stock.setShoworhide("H");
	}		
	
	public boolean checkIdDuplicate(String id) {
		
		return this.stocksRepository.existsById(id);
	}
	
}
