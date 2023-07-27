package com.dokkaebi.repository.StudyRoom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokkaebi.domain.studyroom.HashTag;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {
	
	List<HashTag> findByStudyRoomId(Long roomId);
}
