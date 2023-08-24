package com.dokkaebi.controller;

import com.dokkaebi.service.JwtService;
import com.dokkaebi.service.JwtServiceImpl;
import com.dokkaebi.service.MemberService;
import com.dokkaebi.service.MemberServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/jwt")
@RestController
@RequiredArgsConstructor
public class JwtController {

  private final JwtServiceImpl jwtService;
  private final MemberServiceImpl memberService;

  @GetMapping("/reissue")
  public ResponseEntity<?> reissueTokens(
      HttpServletRequest httpServletRequest) {

    String refreshToken = httpServletRequest.getHeader("refreshToken");
    Long memberId = jwtService.getIdFromToken(refreshToken);
    if (jwtService.isRefreshTokenValid(refreshToken, memberId)) {
      String newAccessToken = jwtService.createAccessToken("id", memberId);
      String newRefreshToken = jwtService.createRefreshToken("id", memberId);
      memberService.saveRefreshToken(memberId, newRefreshToken);

      Map<String, String> body = new HashMap<>();
      body.put("accessToken", newAccessToken);
      body.put("refreshToken", newRefreshToken);
      return new ResponseEntity<>(body, HttpStatus.OK);
    }
    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
  }
}
