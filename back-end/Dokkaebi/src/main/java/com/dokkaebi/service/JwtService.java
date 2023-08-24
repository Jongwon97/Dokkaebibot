package com.dokkaebi.service;

import java.util.Map;

public interface JwtService {

	<T> String createAccessToken(String key, T data);

	<T> String createAccessToken(Map<String, T> data);
	<T> String createRefreshToken(String key, T data);
	<T> String createRefreshToken(Map<String, T> data);
	<T> String create(String key, T data, String subject, long expir);
	<T> String create(Map<String, T> data, String subject, long expir);

	Long getIdFromToken(String token);
	String getNicknameFromToken(String token);

//	Map<String, Object> get(String key);
//	String getUserId();
	boolean checkToken(String jwt);
	
}
