package com.dokkaebi.service;

import com.dokkaebi.domain.Member;

public interface MemberService {

	public boolean registMember(Member member); 		// 회원 가입
	public boolean updateMember(Member member); 		// 회원 정보 수정	
	public Member findMember(Member member);			// 회원 조회
	
}
