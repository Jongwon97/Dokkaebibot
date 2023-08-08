package com.dokkaebi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokkaebi.domain.MemberInterest;

public interface MemberInterestRepository extends JpaRepository<MemberInterest, Long> {

	List<MemberInterest> findByMemberId(Long memberId);
	
	void deleteByMemberId(Long memberId);
}
