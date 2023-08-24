package com.dokkaebi.repository.StudyRoom;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dokkaebi.domain.studyroom.StudyRoom;

import jakarta.transaction.Transactional;

@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {
	
	List<StudyRoom> findTop10ByOrderByCreatedDateDesc();
	List<StudyRoom> findByTitleContaining(String title);
	
	Optional<StudyRoom> findById(Long roomId);
	
	@Transactional
	@Modifying
    @Query("UPDATE StudyRoom sr SET sr.headerImg = :imageUrl WHERE sr.id = :roomId")
    void setStudyRoomImage(@Param("imageUrl") String imageUrl, @Param("roomId") Long roomId);
	
}
