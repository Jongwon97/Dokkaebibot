package com.dokkaebi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dokkaebi.domain.studyroom.StudyRoom;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {

	@Query("select s from StudyRoom s order by s.createdDate desc limit 10")
	List<StudyRoom> findByStudyRoomsRecent10();
}
