package com.dokkaebi.repository.StudyRoom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokkaebi.domain.studyroom.StudyRoomMember;

public interface StudyRoomMemberRepository extends JpaRepository<StudyRoomMember, Long>{

	List<StudyRoomMember> findAllByMemberId(Long memberId);
	
	List<StudyRoomMember> findAllByStudyRoomId(Long roomId);
}
