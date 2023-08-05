package com.dokkaebi.repository.StudyRoom;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dokkaebi.domain.studyroom.chat.Chat;

public interface ChatRepository extends JpaRepository<Chat, Long> {

	
	List<Chat> findAllByStudyRoomId(Long roomId);
}
