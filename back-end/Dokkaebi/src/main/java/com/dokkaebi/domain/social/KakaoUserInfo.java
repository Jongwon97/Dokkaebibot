package com.dokkaebi.domain.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoUserInfo {
  @JsonProperty("kakao_account")
  private KakaoAccount kakaoAccount;
  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class KakaoAccount {
    private Profile profile;
    private String email;
  }
  @Getter
  @JsonIgnoreProperties(ignoreUnknown = true)
  static class Profile {
    private String nickname;
    private String profileImageUrl;
  }

  public String getEmail() {
    return kakaoAccount.getEmail();
  }

  public String getNickname() {
    Profile kakaoProfile = kakaoAccount.getProfile();
    if (kakaoProfile == null) {
      return "";
    }
    return kakaoAccount.getProfile().getNickname();
  }

  public String getProfileImageUrl() {
    Profile kakaoProfile = kakaoAccount.getProfile();
    if (kakaoProfile == null) {
      return "";
    }
    return kakaoAccount.getProfile().getProfileImageUrl();
  }

}
