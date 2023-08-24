package com.dokkaebi.domain.social;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleClient {

  private final RestTemplate restTemplate;
  @Value("${google.client.id}")
  private String clientId;

  @Value("${google.client.secret}")
  private String clientSecret;

  @Value("${google.auth.host}")
  private String authURL;

  @Value("${google.api.host}")
  private String apiHost;

  @Value("${google.url.redirect}")
  private String redirectURL;
  @Value("${front.scheme}")
  private String frontScheme;
  @Value("${front.host}")
  private String frontHost;

  public void getAuthCode(HttpServletResponse httpServletResponse) throws IOException {

    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host(authURL)
        .path("/o/oauth2/v2/auth")
        .queryParam("client_id", clientId)
        .queryParam("redirect_uri", redirectURL)
        .queryParam("response_type", "code")
        .queryParam("scope", "https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile")
        .build();
    httpServletResponse.sendRedirect(uriComponents.toString());
  }

  public String getToken(String authCode) {

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host(apiHost)
        .path("/token")
        .queryParam("client_id", clientId)
        .queryParam("client_secret", clientSecret)
        .queryParam("code", authCode)
        .queryParam("grant_type", "authorization_code")
        .queryParam("redirect_uri", redirectURL)
        .build();

    HttpEntity<?> request = new HttpEntity<>(httpHeaders);

    GoogleToken googleToken = restTemplate
        .postForObject(uriComponents.toString(), request, GoogleToken.class);

    return googleToken.getAccessToken();
  }

  public GoogleUserInfo getUserInfo(String accessToken) {

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    httpHeaders.set("Authorization", "Bearer " + accessToken);

    HttpEntity<?> request = new HttpEntity<>(httpHeaders);

    UriComponents uriComponents = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("people.googleapis.com")
        .path("/v1/people/me")
        .queryParam("personFields", "emailAddresses,names")
        .build();

    return restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, request,
        GoogleUserInfo.class).getBody();
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
