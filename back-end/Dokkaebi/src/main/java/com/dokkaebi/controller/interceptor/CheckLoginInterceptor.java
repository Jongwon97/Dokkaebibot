package com.dokkaebi.controller.interceptor;

import com.dokkaebi.domain.Member;
import com.dokkaebi.repository.MemberRepository;
import com.dokkaebi.service.MemberService;
import java.util.Objects;

import java.util.Optional;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.dokkaebi.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// AccessToken이 유효한지 확인해주는 클래스
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {

  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";

  private JwtService jwtService;
  private MemberRepository memberRepository;

  public CheckLoginInterceptor(JwtService jwtService, MemberRepository memberRepository) {
    super();
    this.jwtService = jwtService;
    this.memberRepository = memberRepository;
  }


  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    if (PreflightChecker.isPreflightRequest(request)) {
      return true;
    }

    String accessToken = request.getHeader("accessToken"); // 헤더에서 토큰 꺼냄
    // AcessToken이 유효한지 체크
    if (jwtService.checkToken(accessToken)) {
      Long id = jwtService.getIdFromToken(accessToken); // 토큰에서 id값을 꺼냄
      Optional<Member> member = memberRepository.findById(id);
      if (member.isPresent()) {
        request.setAttribute("id", id); // reqeust에 id를 담아서 controller로 보냄
        return true;
      } else {
        System.out.println("MEMBER IS NOT REGISTERED");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("MEMBER IS NOT REGISTERED");
        return false;
      }
    }
    System.out.println("TOKEN IS NOT VALID NEED REFRESHTOKEN");
    response.setStatus(HttpStatus.UNAUTHORIZED.value());
    response.getWriter().write("TOKEN IS NOT VALID NEED REFRESHTOKEN");
    return false;
  }
}
