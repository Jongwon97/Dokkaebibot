package com.dokkaebi.repository.StudyRoom;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dokkaebi.domain.studyroom.StudyRoomMember;

public interface StudyRoomMemberRepository extends JpaRepository<StudyRoomMember, Long>{

	List<StudyRoomMember> findAllByMemberId(Long memberId);
	
	List<StudyRoomMember> findAllByStudyRoomId(Long roomId);
	
	StudyRoomMember findByMemberIdAndStudyRoomId(Long memberId, Long roomId); // 해당 방에 멤버가 존재하는지 확인
	
	// 스터디룸 활동(입장,퇴장) 여부
    @Modifying
    @Query("UPDATE StudyRoomMember s SET s.isActive = :isActive WHERE s.member.id = :memberId AND s.studyRoom.id = :roomId")
    void updateIsActiveByMemberIdAndRoomId(@Param("memberId") Long memberId, @Param("roomId") Long roomId, @Param("isActive") int isActive);

	// 시간 변경
    @Modifying
    @Query("UPDATE StudyRoomMember s SET s.time = :time WHERE s.member.id = :memberId AND s.studyRoom.id = :roomId")
    void updateTimeByMemberIdAndRoomId(@Param("memberId") Long memberId, @Param("roomId") Long roomId, @Param("time") int time);
    
	// StartDate 변경
    @Modifying
    @Query("UPDATE StudyRoomMember s SET s.startTime = :startTime WHERE s.member.id = :memberId AND s.studyRoom.id = :roomId")
    void updateStartTimeByMemberIdAndRoomId(@Param("memberId") Long memberId, @Param("roomId") Long roomId, @Param("startTime") LocalTime startTime);
    
	@Query("SELECT srm.studyRoom.id FROM StudyRoomMember srm WHERE srm.isActive = 1 AND srm.member.id = :memberId")
	Long findRoomIdByMemberId(@Param("memberId") Long memberId);
}
