package com.dokkaebi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dokkaebi.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	// 이메일 조회: 회원가입 할 때 이메일이 이미 존재하는지 확인
	// @Query("select m from Member m where m.user_email=:user_email")
	Member findByEmail(@Param("email") String email);

	// 로그인: ID, PW를 통회 유저 조회
	// @Query("SELECT m FROM Member m WHERE m.userEmail = :userEmail and m.userPw = :userPw")
	Member findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

	// 기본키로 Member 조회
	Member findById(long userId);
  
	// 토큰 반환
	@Query("select m.refreshToken from Member m where m.id = :id")
	String findByRefreshToken(@Param("id") Long id);

	@Query("SELECT m FROM Member m WHERE m.email LIKE :email")
	List<Member> findLikeEmail(@Param("email") String email);
}