package com.dokkaebi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.ZandiDto;
import com.dokkaebi.service.JwtService;
import com.dokkaebi.service.MemberInfoService;
import com.dokkaebi.service.MemberService;
import com.dokkaebi.service.StudyDataService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/members")
public class MemberController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";


	private MemberService ms;
	private JwtService jwtService;
	private MemberInfoService mis;
	private StudyDataService sds;
	
	@Autowired
	public MemberController(MemberService ms, JwtService jwtService, MemberInfoService mis, StudyDataService sds) {
		this.ms = ms;
		this.jwtService = jwtService;
		this.mis = mis;
		this.sds=sds;
	}

	// 로그인 처리
	// http://localhost:8080/dokkaebi/api/members/login
	@PostMapping("/login")
	protected ResponseEntity<?> login(@RequestBody Member member) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		try {
			Member loginUser = ms.login(member);
			if (loginUser != null) {
				// 토큰에 담을 데이터
				Map<String, Object> tokenMap = new HashMap<>();
				tokenMap.put("id", loginUser.getId());
				tokenMap.put("nickname", loginUser.getNickname());
				// AccessToken, RefreshToken 생성 후 refreshToken DB에 저장
				String accessToken = jwtService.createAccessToken(tokenMap);
				String refreshToken = jwtService.createRefreshToken(tokenMap);
				ms.saveRefreshToken(loginUser.getId(), refreshToken);


				// client로 보낼 데이터
				resultMap.put("accessToken", accessToken);
				resultMap.put("refreshToken", refreshToken);
				resultMap.put("id", loginUser.getId());
				resultMap.put("nickname", loginUser.getNickname());
				resultMap.put("iconNumber", loginUser.getIconNumber());
				resultMap.put("email", loginUser.getEmail());
				resultMap.put("haveDevice", (loginUser.getDevice() != null));
				resultMap.put("message", SUCCESS);
				resultMap.put("isAdmin", loginUser.isAdmin());
				resultMap.put("info", loginUser.getIntroduction());
				status = HttpStatus.ACCEPTED;
			} else {
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 로그 아웃 처리
	@GetMapping("/logout/check")
	public ResponseEntity<?> removeToken(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			ms.deleteRefreshToken((Long) request.getAttribute("id"));
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	// 회원 가입
	@PostMapping("/register")
	protected ResponseEntity<?> registMember(@RequestBody Member member) throws Exception {
		boolean flag = ms.registMember(member);
		if (flag) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			System.out.println("이미 존재하는 이메일이거나, 이메일, 비밀번호 형식이 올바르지 않습니다.");
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}

	// 회원 정보 수정
	@PutMapping
	protected ResponseEntity<String> updateMember(@RequestBody Member member) throws Exception {
		if (ms.updateMember(member)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		} else {
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		}
	}

	// get member info
	@GetMapping("info/check")
	public ResponseEntity<?> getMemberInfo(
			HttpServletRequest httpServletRequest) {
		Long memberId = (Long) httpServletRequest.getAttribute("id");
		return new ResponseEntity<>(ms.userInfo(memberId), HttpStatus.OK);
	}

	// search member by email
	@GetMapping("search")
	public ResponseEntity<?> searchMember(
			@RequestParam("email") String email,
			@RequestParam("id") Long memberId) {
		return new ResponseEntity<>(ms.findLikeEmail(email, memberId), HttpStatus.OK);
	}

	// 유저 관심사 등록
	@PostMapping("/regist/interests/check")
	public ResponseEntity<?> registMemberInterest(HttpServletRequest httpServletRequest,
			@RequestBody List<String> tagNames) {
		Long memberId = (Long) httpServletRequest.getAttribute("id");
		Member member = new Member();
		member.setId(memberId);
		mis.registInterests(member, tagNames);
		// 등록하고 태그명들만 조회해서 반환
		return new ResponseEntity<List<String>>(mis.findMemberInterests(member), HttpStatus.OK);
	}

	// 유저 관심사 조회
	@GetMapping("/find/interests/check")
	public ResponseEntity<?> findUserInterests(HttpServletRequest httpServletRequest) {
		Long memberId = (Long) httpServletRequest.getAttribute("id");
		Member member = new Member();
		member.setId(memberId);
		return new ResponseEntity<List<String>>(mis.findMemberInterests(member), HttpStatus.OK);
	}

	// 유저 한줄소개 등록
	@PostMapping("/regist/introduction/check")
	public ResponseEntity<?> regisMemberIntroduction(HttpServletRequest httpServletRequest,
			@RequestBody String introduction) {
		Long memberId = (Long) httpServletRequest.getAttribute("id");
		Member member = new Member();
		member.setId(memberId);
		try {
			mis.registUserIntoduction(member, new ObjectMapper().readValue(introduction, String.class));
			// 등록하고 태그명들만 조회해서 반환
			return new ResponseEntity<String>(introduction, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// 유저 한줄소개 조회
	@GetMapping("/find/introduction/check")
	public ResponseEntity<?> findUserIntroduction(HttpServletRequest httpServletRequest) {
		Long memberId = (Long) httpServletRequest.getAttribute("id");
		Member member = new Member();
		member.setId(memberId);
		return new ResponseEntity<String>(mis.findMemberIntroduction(member), HttpStatus.OK);
	}

	// 닉네임 바꾸기
	@PutMapping("/nickname/check")
	public ResponseEntity<?> updateNickname(
			HttpServletRequest httpServletRequest,
			@RequestBody String changeTo) {

		Long memberId = (Long) httpServletRequest.getAttribute("id");
		try {
			ms.updateNickname(memberId, new ObjectMapper().readValue(changeTo, String.class));
		} catch (Exception e) {
			return  new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}

	// 비밀번호 변경
	@PutMapping("/password/check")
	public ResponseEntity<?> updatePassword(
			HttpServletRequest httpServletRequest,
			@RequestBody Map<String, String> obj) {

		Long memberId = (Long) httpServletRequest.getAttribute("id");
		try {
			if (obj.get("changeTo").equals(obj.get("changeToConfirm"))
					&& ms.updatePassword(memberId, obj.get("changeTo"), obj.get("pwBefore"))) {
					return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return  new ResponseEntity<String>(FAIL, HttpStatus.EXPECTATION_FAILED);
	}
	
	// 잔디 데이터 반환
	@GetMapping("/zandi/check")
	public ResponseEntity<List<ZandiDto>> findUserZandies(HttpServletRequest httpServletRequest) {
		Long memberId = (Long) httpServletRequest.getAttribute("id");
		return new ResponseEntity<List<ZandiDto>>(sds.getZandiDto(memberId), HttpStatus.OK);
	}
}
