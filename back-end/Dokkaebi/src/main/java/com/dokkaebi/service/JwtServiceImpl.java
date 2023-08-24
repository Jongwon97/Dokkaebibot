package com.dokkaebi.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

	private final MemberServiceImpl memberService;

	public static final Logger logger = LoggerFactory.getLogger(JwtServiceImpl.class);

//	SALT는 토큰 유효성 확인 시 사용하기 때문에 외부에 노출되지 않게 주의해야 한다.
	private static final String SALT = "ssafySecret";
	
	private static final int ACCESS_TOKEN_EXPIRE_MINUTES = 30; // 분단위
	private static final int REFRESH_TOKEN_EXPIRE_MINUTES = 2; // 주단위

	// AccessToken 생성
	@Override
	public <T> String createAccessToken(String key, T data) {
		return create(key, data, "access-token", 1000 * 60 * ACCESS_TOKEN_EXPIRE_MINUTES);
	}
	@Override
	public <T> String createAccessToken(Map<String, T> data) {
		return create(data, "access-token", 1000 * 60 * ACCESS_TOKEN_EXPIRE_MINUTES);
	}

	// RefreshToken 생성
	// AccessToken에 비해 유효기간을 길게...
	@Override
	public <T> String createRefreshToken(String key, T data) {
		return create(key, data, "refresh-token", 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MINUTES);
//		return create(key, data, "refresh-token", 1000 * 30 * ACCESS_TOKEN_EXPIRE_MINUTES); // 30초
	}

	@Override
	public <T> String createRefreshToken(Map<String, T> data) {
		return create(data, "refresh-token", 1000 * 60 * 60 * 24 * 7 * REFRESH_TOKEN_EXPIRE_MINUTES);
//		return create(key, data, "refresh-token", 1000 * 30 * ACCESS_TOKEN_EXPIRE_MINUTES); // 30초
	}

	//Token 발급
	/**
	 * key : Claim에 셋팅될 key 값
	 * data : Claim에 셋팅 될 data 값
	 * subject : payload에 sub의 value로 들어갈 subject값
	 * expire : 토큰 유효기간 설정을 위한 값
	 * jwt 토큰의 구성 : header + payload + signature
	 */
	@Override
	public <T> String create(String key, T data, String subject, long expire) {
		// Payload 설정 : 생성일 (IssuedAt), 유효기간 (Expiration), 
		// 토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
		Claims claims = Jwts.claims()
				// 토큰 제목 설정 ex) access-token, refresh-token
				.setSubject(subject)
				// 생성일 설정
				.setIssuedAt(new Date()) 
				// 만료일 설정 (유효기간)
				.setExpiration(new Date(System.currentTimeMillis() + expire)); 
		
		// 저장할 data의 key, value
		claims.put(key, data); 
		
		// 문자열로 된 jwt 생성
		return Jwts.builder()
				// Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
				.setHeaderParam("typ", "JWT")
				// Payload 설정
				.setClaims(claims)
				// Signature 설정 : secret key를 활용한 암호화.
				.signWith(SignatureAlgorithm.HS256, this.generateKey())
				.compact();
	}
	@Override
	public <T> String create(Map<String, T> data, String subject, long expire) {
		// Payload 설정 : 생성일 (IssuedAt), 유효기간 (Expiration),
		// 토큰 제목 (Subject), 데이터 (Claim) 등 정보 세팅.
		Claims claims = Jwts.claims()
				// 토큰 제목 설정 ex) access-token, refresh-token
				.setSubject(subject)
				// 생성일 설정
				.setIssuedAt(new Date())
				// 만료일 설정 (유효기간)
				.setExpiration(new Date(System.currentTimeMillis() + expire));

		// 저장할 data의 key, value
		for (String key : data.keySet()) {
			claims.put(key, data.get(key));
		}

		// 문자열로 된 jwt 생성
		return Jwts.builder()
				// Header 설정 : 토큰의 타입, 해쉬 알고리즘 정보 세팅.
				.setHeaderParam("typ", "JWT")
				// Payload 설정
				.setClaims(claims)
				// Signature 설정 : secret key를 활용한 암호화.
				.signWith(SignatureAlgorithm.HS256, this.generateKey())
				.compact();
	}

	// Signature 설정에 들어갈 key 생성.
	private byte[] generateKey() {
		byte[] key = null;
		try {
			// charset 설정 안하면 사용자 플랫폼의 기본 인코딩 설정으로 인코딩 됨.
			key = SALT.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}

		return key;
	}

	// 로그인을 해야지 볼수 있는 페이지에서 로그인을 한 사람인지 검사하는 코드
	//	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
	@Override
	public boolean checkToken(String jwt) {
		try {
//			Json Web Signature? 서버에서 인증을 근거로 인증정보를 서버의 private key로 서명 한것을 토큰화 한것
//			setSigningKey : JWS 서명 검증을 위한  secret key 세팅
//			parseClaimsJws : 파싱하여 원본 jws 만들기
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
//			Claims 는 Map의 구현체 형태
			logger.debug("claims: {}", claims);
			return true;
		} catch (Exception e) {
//			if (logger.isInfoEnabled()) {
//				e.printStackTrace();
//			} else {
			logger.error(e.getMessage());
//			}
//			throw new UnauthorizedException();
//			개발환경
			return false;
		}
	}

	public boolean isRefreshTokenValid(String refreshToken, Long memberId) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(refreshToken);
			if (refreshToken.equals(memberService.getRefreshToken(memberId))) {
				return true;
			}
			//			Claims 는 Map의 구현체 형태
			logger.debug("claims: {}", claims);
			return false;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public Long getIdFromToken(String token) {
		Claims body = Jwts.parser()
				.setSigningKey(this.generateKey())
				.parseClaimsJws(token)
				.getBody();
		return body.get("id", Long.class);
	}

	@Override
	public String getNicknameFromToken(String token) {
		Claims body = Jwts.parser()
				.setSigningKey(this.generateKey())
				.parseClaimsJws(token)
				.getBody();
		return body.get("nickname", String.class);
	}


//	@Override
//	public Map<String, Object> get(String key) {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
//				.getRequest();
//		String jwt = request.getHeader("accessToken");
//		Jws<Claims> claims = null;
//		try {
//			claims = Jwts.parser().setSigningKey(SALT.getBytes("UTF-8")).parseClaimsJws(jwt);
//		} catch (Exception e) {
////			if (logger.isInfoEnabled()) {
////				e.printStackTrace();
////			} else {
//			logger.error(e.getMessage());
////			}
//			throw new UnAuthorizedException();
////			개발환경
////			Map<String,Object> testMap = new HashMap<>();
////			testMap.put("userid", userid);
////			return testMap;
//		}
//		Map<String, Object> value = claims.getBody();
//		logger.info("value : {}", value);
//		return value;
//	}

//	@Override
//	public String getUserId() {
//		return (String) this.get("user").get("userId");
//	}

}
