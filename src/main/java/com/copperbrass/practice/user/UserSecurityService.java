package com.copperbrass.practice.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.copperbrass.practice.SiteUser;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
	//스프링 시큐리티가 로그인 시 사용할 UserSecurityService는 스프링 시큐리티가 제공하는 UserDetailsService 인터페이스를 구현(implements)해야 한다

    private final UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    	// UserDetailsService는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스
//    	
//        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
//        if (_siteUser.isEmpty()) {
//            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
//        }
//        SiteUser siteUser = _siteUser.get();
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if ("admin".equals(username)) {
//            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
//        }
//        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
//    }
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        SiteUser siteUser = _siteUser.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        // 디버깅: 권한 출력
        authorities.forEach(auth -> System.out.println("Authority: " + auth.getAuthority()));

        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }

    
}
