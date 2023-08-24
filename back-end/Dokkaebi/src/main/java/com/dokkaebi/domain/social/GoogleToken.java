package com.dokkaebi.domain.social;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class GoogleToken {
  @JsonProperty("token_type")
  private String tokenType;

  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("expires_in")
  private String expiresIn;

  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("scope")
  private String scope;
}
