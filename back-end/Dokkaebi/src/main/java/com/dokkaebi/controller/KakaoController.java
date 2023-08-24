package com.dokkaebi.controller;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.social.KakaoClient;
import com.dokkaebi.service.KakaoService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequestMapping("/api/kakao")
@RestController
@RequiredArgsConstructor
public class KakaoController {

  private final KakaoClient kakaoClient;
  private final KakaoService kakaoService;

  @GetMapping("/login")
  public void kakaoLoginOrRegister(HttpServletResponse httpServletResponse) throws Exception{
    kakaoClient.getAuthCode(httpServletResponse);
  }

  @GetMapping("/redirect")
  public ResponseEntity<?> kakaoRedirect(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) throws Exception{

    String accessToken = kakaoClient.getToken(httpServletRequest);
    Member member = kakaoService.loginOrRegister(accessToken);
    String accessJWT = kakaoService.makeAccessJWT(member);
    String refreshJWT = kakaoService.makeRefreshJWT(member);
    kakaoService.saveRefreshToken(member, refreshJWT);

    String frontURI = kakaoClient.getFrontURI(accessJWT, refreshJWT);

    // redirect to bridge page
    // 302 redirect
    return ResponseEntity
        .status(HttpStatus.FOUND) // 302
        .location(URI.create(frontURI))
        .build();
  }
}
