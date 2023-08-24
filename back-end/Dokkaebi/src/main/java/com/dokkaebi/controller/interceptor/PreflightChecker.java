package com.dokkaebi.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import org.springframework.http.HttpMethod;

public class PreflightChecker {
  // PreFlight 처리
  public static boolean isPreflightRequest(HttpServletRequest request) {
    return isOptions(request) && hasHeaders(request) && hasMethod(request) && hasOrigin(request);
  }

  private static boolean isOptions(HttpServletRequest request) {
    return request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.toString());
  }

  private static boolean hasHeaders(HttpServletRequest request) {
    return Objects.nonNull(request.getHeader("Access-Control-Request-Headers"));
  }

  private static boolean hasMethod(HttpServletRequest request) {
    return Objects.nonNull(request.getHeader("Access-Control-Request-Method"));
  }

  private static boolean hasOrigin(HttpServletRequest request) {
    return Objects.nonNull(request.getHeader("Origin"));
  }
}
