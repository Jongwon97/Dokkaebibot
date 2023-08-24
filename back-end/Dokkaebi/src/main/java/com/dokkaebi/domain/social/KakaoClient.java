package com.dokkaebi.domain.social;

import com.dokkaebi.domain.social.KakaoToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoClient {

  @Value("${kakao.api.key}")
  private String apiKey;
  @Value("${kakao.api.host}")
  private String apiURL;
  @Value("${kakao.auth.host}")
  private String authURL;
  @Value("${kakao.url.redirect}")
  private String redirectURL;

  @Value("${front.scheme}")
  private String frontScheme;
  @Value("${front.host}")
  private String frontHost;

  private final RestTemplate restTemplate;

  // Redirect to get authorization code
  public void getAuthCode(HttpServletResponse httpServletResponse) throws Exception {
    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host(authURL)
        .path("/oauth/authorize")
        .queryParam("client_id", apiKey)
        .queryParam("redirect_uri", redirectURL)
        .queryParam("response_type", "code")
        .build();
    httpServletResponse.sendRedirect(uriComponents.toString());
  }

  // Get Token from Kakao using authorization code
  public String getToken(HttpServletRequest httpServletRequest) throws Exception {
    String authCode = httpServletRequest.getParameter("code");

    // make Header
    HttpHeaders httpHeaders = new HttpHeaders();
    // declare request data type reference in Kakao Development Docs
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    // make Body
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", apiKey);
    body.add("redirect_uri", redirectURL);
    body.add("code", authCode);

    HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host(authURL)
        .path("/oauth/token")
        .build();
    KakaoToken kakaoToken = restTemplate
        .postForObject(uriComponents.toString(), request, KakaoToken.class);

    // if access fails
    assert kakaoToken != null;

    return kakaoToken.getAccessToken();
  }

  public KakaoUserInfo getUserInfo(String accessToken) {
    // make Header
    HttpHeaders httpHeaders = new HttpHeaders();
    // declare request data type, reference in Kakao Development Docs
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    // add token in header
    httpHeaders.set("Authorization", "Bearer " + accessToken);

    // make Body
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");
    HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host(apiURL)
        .path("/v2/user/me")
        .build();
    return restTemplate.postForObject(uriComponents.toString(), request, KakaoUserInfo.class);
  }

  public String getFrontURI(String accessJWT, String refreshJWT) {
    return UriComponentsBuilder.newInstance()
        .scheme(frontScheme)
        .host(frontHost)
        .path("/bridge")
        .queryParam("accessToken", accessJWT)
        .queryParam("refreshToken", refreshJWT)
        .build()
        .toString();
  }
}
