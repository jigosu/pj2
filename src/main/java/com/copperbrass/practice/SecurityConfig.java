package com.copperbrass.practice;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

@Configuration //스프링의 환경 설정 파일
@EnableWebSecurity		// 모든요청URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {		
    	//내부적으로 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 이 클래스가 필터로 적용되어 URL별로 특별한 설정을 할 수 있음
        http
        
            .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())			//인증되지 않은 모든페이지의 요청을 허락, 로그인 X 페이지 요청 허락
            .csrf((csrf) -> csrf												///h2-console/로 시작하는 모든 URL은 CSRF 검증을 하지 않는다
//            		.disable())
            		.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
            .headers((headers) -> headers		
                    .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))) 
            .formLogin((formLogin) -> formLogin
                    .loginPage("/user/login")
                    .defaultSuccessUrl("/copperbrass"))      
            .logout((logout) -> logout
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/copperbrass")
                    .invalidateHttpSession(true))    
            
        ;
        return http.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}
