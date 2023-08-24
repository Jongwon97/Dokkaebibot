package com.dokkaebi.service;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.social.GoogleClient;
import com.dokkaebi.domain.social.GoogleUserInfo;
import com.dokkaebi.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class GoogleService {
  private final GoogleClient googleClient;
  private final MemberRepository memberRepository;
  private final JwtService jwtService;

  public Member loginOrRegister(String accessToken) {

    GoogleUserInfo googleUserInfo = googleClient.getUserInfo(accessToken);

    // check user info
    if (memberRepository.findByEmail(googleUserInfo.getEmail()) == null) {
      // if user email does not exist in DB
      Member member = new Member();
      member.setEmail(googleUserInfo.getEmail());
      member.setNickname(googleUserInfo.getNickname());
      member.setPassword("googlePassword");
      member.setIconNumber(6);
      memberRepository.save(member);
      return member;
    }
    return memberRepository.findByEmail(googleUserInfo.getEmail());
  }



  public String makeAccessJWT(Member member) {
    Map<String, Object> data = new HashMap<>();
    data.put("id", member.getId());
    data.put("nickname", member.getNickname());

    return jwtService.createAccessToken(data);
  }

  public String makeRefreshJWT(Member member) {
    Map<String, Object> data = new HashMap<>();
    data.put("id", member.getId());
    data.put("nickname", member.getNickname());
    return jwtService.createRefreshToken(data);
  }
  public void saveRefreshToken(Member member, String refreshJWT) {
    member.setRefreshToken(refreshJWT);
  }

}
