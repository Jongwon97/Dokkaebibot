package com.dokkaebi.controller.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dokkaebi.repository.MemberRepository;
import com.dokkaebi.service.JwtService;
import com.dokkaebi.service.MemberService;

// Interceptor 등록해주는 클래스
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private JwtService jwtService;
	private MemberService ms;
	private MemberRepository memberRepository;
	
	@Autowired
	public WebConfig(JwtService jwtService, MemberService ms, MemberRepository memberRepository) {
		super();
		this.jwtService = jwtService;
		this.ms = ms;
		this.memberRepository = memberRepository;
	}

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CheckLoginInterceptor(jwtService, memberRepository))
        .addPathPatterns("/api/**/check") // 특정 URL 패턴에만 적용
        .order(1); // 인터셉터의 실행 순서 지정 (낮은 값이 먼저 실행);
    }
}