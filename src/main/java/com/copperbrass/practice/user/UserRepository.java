package com.copperbrass.practice.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.copperbrass.practice.SiteUser;


public interface UserRepository extends JpaRepository<SiteUser, Long> {
	
	Optional<SiteUser> findByusername(String username);
	
	
	@Query("SELECT u FROM SiteUser u where u.username = ?1")
	SiteUser findByusername1(String username);	
	
}
