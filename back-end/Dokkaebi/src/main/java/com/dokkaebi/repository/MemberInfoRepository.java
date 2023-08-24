package com.dokkaebi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokkaebi.domain.MemberInterest;

import jakarta.transaction.Transactional;

public interface MemberInfoRepository extends JpaRepository<MemberInterest, Long> {

	List<MemberInterest> findByMemberId(Long memberId);
	
	void deleteByMemberId(Long memberId);
	
    @Query("SELECT m.introduction FROM Member m WHERE m.id = :memberId")
    String findIntroductionByMemberId(@Param("memberId") Long memberId);
	
	@Transactional
	@Modifying
    @Query("UPDATE Member m SET m.introduction = :introduction WHERE m.id = :memberId")
    void updateMemberIntroduction(@Param("memberId") Long memberId, @Param("introduction") String introduction);
}
