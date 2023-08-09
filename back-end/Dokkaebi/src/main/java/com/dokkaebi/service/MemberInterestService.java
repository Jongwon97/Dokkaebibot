package com.dokkaebi.service;

import java.util.List;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberInterest;

public interface MemberInterestService {

	// 멤버별 관심사 등록
	public Boolean registInterests(Member member, List<String> tagNames);
	
	// 멤버별 관심사 조회
	public List<String> findMemberInterests(Member member);
	
	
}
