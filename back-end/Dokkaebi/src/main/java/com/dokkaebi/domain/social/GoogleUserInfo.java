package com.dokkaebi.domain.social;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserInfo {
  @JsonProperty("resourceName")
  private String resourceName;

  @JsonProperty("emailAddresses")
  private List<EmailAddress> emailAddress;

  @JsonProperty("names")
  private List<Name> names;

  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  static class EmailAddress {
    @JsonProperty("value")
    private String email;
  }
  @JsonIgnoreProperties(ignoreUnknown = true)
  @Getter
  static class Name {
    @JsonProperty("displayName")
    private String nickname;
  }

  public String getEmail() {
    return emailAddress.get(0).getEmail();
  }

  public String getNickname() {
    return names.get(0).getNickname();
  }
}
