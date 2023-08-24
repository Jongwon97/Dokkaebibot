package com.dokkaebi.service;

import static java.util.stream.Collectors.toList;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberResponseDTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface MemberService {

	public Member login(Member member); 								// 로그인
	public MemberResponseDTO userInfo(Long memberId);					// id로 유저 정보 반환
	
	public void saveRefreshToken(Long memberId, String refreshToken);	// 토큰 저장
	public String getRefreshToken(Long id);								// 토큰 반환
	public void deleteRefreshToken(Long userId);						// 토큰 삭제
	
	public boolean registMember(Member member); 		// 회원 가입
	public boolean updateMember(Member member); 		// 회원 정보 수정
	public boolean deleteMember(String userId);			// 회원 삭제	

	public List<MemberResponseDTO> findLikeEmail(String email, Long memberId);

	public boolean updateNickname(Long memberId, String changeTo);

	public boolean updatePassword(Long memberId, String changeTo, String pwBefore);

}
