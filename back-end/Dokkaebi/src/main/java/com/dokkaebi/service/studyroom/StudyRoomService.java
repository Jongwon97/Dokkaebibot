package com.dokkaebi.service.studyroom;

import java.time.LocalTime;
import java.util.List;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.studyroom.SocketStudyRoomMemberDto;
import com.dokkaebi.domain.studyroom.StudyRoom;
import com.dokkaebi.domain.studyroom.StudyRoomDto;
import com.dokkaebi.domain.studyroom.StudyRoomMember;
import com.dokkaebi.domain.studyroom.StudyRoomMemberDto;

public interface StudyRoomService {
	public Long createStudyRoom(StudyRoom studyRoom, Member member); // 스터디룸 생성
	public List<StudyRoomDto> getRecent10StudyRooms(); 				// 가장 최근에 생성된 스터디룸 10개 반환
	public List<StudyRoomDto> getStudyRoomsByTitle(String title);		// title 포함 조회
	public List<StudyRoomDto> getMyStudyRooms(Long memberId);		    // 자신이 들어가 있는 스터디룸 조회
	public List<StudyRoomDto> getRecommendedStudyRooms(Long memberId);	// 유저별 추천 스터디룸
	public List<StudyRoomDto> getTotalStudyRooms(); // 전체 스터디룸 조회
	
	public List<StudyRoomMemberDto> getStudyRoomMembers(Long roomId);	// 스터디방에 속한 멤버 반환
	public StudyRoomMemberDto getStudyRoomMember(Long roomId,Long memberId); // 스터디방 새로 가입한 멤버 한명 반환
	public StudyRoomDto getStudyRoomDto(Long roomId);					// StudyRoomDetail 정보 반환
	
	public String enterStudyRoom(StudyRoomMember studyRoomMember);	// 스터디룸 입장
	public boolean exitStudyRoom(Long memberId, Long roomId); 		// 스터디룸 나가기
	public boolean deleteStudyRoom(Long roomId);					// 스터디룸 삭제
	
	public boolean setStudyRoomImage(String imageUrl, Long roomId);
	
	public boolean setStudyRoomMemberIsActive(Long memberId, Long roomId, int isActive); // 스터디룸 활동(입장,퇴장) 여부
	
	public boolean setStudyRoomMemberTime(Long memberId, Long roomId, int time);
	public boolean setStudyRoomMemberStartTime(Long memberId, Long roomId);
	
	
}
