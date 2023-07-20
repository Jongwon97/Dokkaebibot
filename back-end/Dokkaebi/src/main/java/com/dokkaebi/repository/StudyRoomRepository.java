package com.dokkaebi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dokkaebi.domain.studyroom.StudyRoom;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {

}
