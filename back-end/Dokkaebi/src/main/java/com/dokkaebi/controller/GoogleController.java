package com.dokkaebi.controller;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.social.GoogleClient;
import com.dokkaebi.service.GoogleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/google")
@RestController
@RequiredArgsConstructor
public class GoogleController {

  private final GoogleClient googleClient;
  private final GoogleService googleService;

  @GetMapping("/login")
  public void googleLoginOrRegister(HttpServletResponse httpServletResponse) {
    try {
      googleClient.getAuthCode(httpServletResponse);
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  @GetMapping("/redirect")
  public ResponseEntity<?> googleRedirect(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {

    String accessToken = googleClient.getToken(httpServletRequest.getParameter("code"));
    Member member  = googleService.loginOrRegister(accessToken);
    String accessJWT = googleService.makeAccessJWT(member);
    String refreshJWT = googleService.makeRefreshJWT(member);
    googleService.saveRefreshToken(member, refreshJWT);

    String frontURI = googleClient.getFrontURI(accessJWT, refreshJWT);

    return ResponseEntity
        .status(HttpStatus.FOUND)
        .location(URI.create(frontURI))
        .build();
  }
}
