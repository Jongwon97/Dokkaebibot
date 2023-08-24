package com.dokkaebi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberInterest;
import com.dokkaebi.repository.MemberInfoRepository;
import com.dokkaebi.repository.MemberRepository;

@Service
@Transactional
public class MemberInfoServiceImpl implements MemberInfoService{

	@Autowired
	private MemberInfoRepository MIR;
	
	@Autowired
	private MemberRepository MR;
	
	// 유저별 관심사 등록
	@Override
	public Boolean registInterests(Member member, List<String> tagnames) {
		MIR.deleteByMemberId(member.getId()); // 존재하는 태그들 모두 삭제
		
		List<MemberInterest> memberInterests=new ArrayList<>();
		for(String tagname:tagnames) {
			MemberInterest memberInterest=new MemberInterest();
			memberInterest.setMember(member);
			memberInterest.setTagName(tagname);
			memberInterests.add(memberInterest);
		}
		MIR.saveAll(memberInterests);
		return true;
	}

	// 유저별 관심사 조회
	@Override
	public List<String> findMemberInterests(Member member) {
		List<MemberInterest> memberInterests=MIR.findByMemberId(member.getId());
		List<String> responseData=new ArrayList<>();
		for(int i=0;i<memberInterests.size();i++) {
			responseData.add(memberInterests.get(i).getTagName());
		}
		return responseData;
	}

	// 유저 한줄소개 등록
	@Override
	public Boolean registUserIntoduction(Member member, String introduction) {
		MIR.updateMemberIntroduction(member.getId(), introduction);
		return true;
	}

	// 유저 한줄 소개 조회
	@Override
	public String findMemberIntroduction(Member member) {
		return MIR.findIntroductionByMemberId(member.getId());
	}

}
