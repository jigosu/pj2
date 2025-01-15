package com.copperbrass.practice;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StocksRepository extends JpaRepository<stocks, Integer> {

	@Query("SELECT s FROM stocks s WHERE s.mainshow LIKE '%main1%' and s.showorhide = 'S'")
	List<stocks> findMainBestseller1();
	
	List<stocks> findByMainshowStartsWith(String id);
	
	
	
	
	@Query("SELECT s FROM stocks s where s.showorhide = 'S' order by s.num desc limit 4")
	List<stocks> find4stocksByNum();	
	
	@Query("SELECT s FROM stocks s where s.num < ?1 and s.showorhide = 'S' order by s.num desc limit 4")
	List<stocks> find4stocksByNum(int num);
	
	@Query("SELECT s FROM stocks s where s.category = ?1 and s.showorhide = 'S' order by s.num desc limit 4")
	List<stocks> find4stocksByNum(String category);	
	
	@Query("SELECT s FROM stocks s where s.category = ?1 and s.num < ?2  and s.showorhide = 'S' order by s.num desc limit 4")
	List<stocks> find4stocksByNum(String category, int num);	
	
	stocks findByNum(int num);
	
	stocks findByName(String name);
	
	stocks findById(String id);

	
	boolean existsById(String id);
	

	
}


//https://www.baeldung.com/spring-jpa-like-queries     >>> like 쿼리 예문