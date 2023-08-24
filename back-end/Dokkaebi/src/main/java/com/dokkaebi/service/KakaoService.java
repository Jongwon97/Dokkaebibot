package com.dokkaebi.service;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.social.KakaoClient;
import com.dokkaebi.domain.social.KakaoUserInfo;
import com.dokkaebi.repository.MemberRepository;
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
public class KakaoService {

  private final KakaoClient kakaoClient;

  private final MemberRepository memberRepository;

  private final JwtServiceImpl jwtServiceImpl;

  public Member loginOrRegister(String accessToken) {
    KakaoUserInfo kakaoUserInfo = kakaoClient.getUserInfo(accessToken);
    // check user info
    if (memberRepository.findByEmail(kakaoUserInfo.getEmail()) == null) {
      // if user email does not exist in DB
      Member member = new Member();
      member.setEmail(kakaoUserInfo.getEmail());
      member.setNickname(kakaoUserInfo.getNickname());
      member.setPassword("kakaoPassword");
      member.setIconNumber(0);
      memberRepository.save(member);
      return member;
    }
    return memberRepository.findByEmail(kakaoUserInfo.getEmail());
  }

  public String makeAccessJWT(Member member) {
    Map<String, Object> data = new HashMap<>();
    data.put("id", member.getId());
    data.put("nickname", member.getNickname());

    return jwtServiceImpl.createAccessToken(data);
  }

  public String makeRefreshJWT(Member member) {
    Map<String, Object> data = new HashMap<>();
    data.put("id", member.getId());
    data.put("nickname", member.getNickname());
    return jwtServiceImpl.createRefreshToken(data);
  }

  public void saveRefreshToken(Member member, String refreshJWT) {
    member.setRefreshToken(refreshJWT);
  }
}
