package com.dokkaebi.service.studyroom;

import java.util.List;

import com.dokkaebi.domain.studyroom.chat.ChatDto;

public interface ChatService {

	public boolean registMessage(ChatDto chatDto); // DB에 메세지 반영
	
	public List<ChatDto> getStudyRoomChats(Long roomId);
}
