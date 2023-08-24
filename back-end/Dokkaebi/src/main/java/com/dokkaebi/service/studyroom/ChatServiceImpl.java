package com.dokkaebi.service.studyroom;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.studyroom.StudyRoom;
import com.dokkaebi.domain.studyroom.chat.Chat;
import com.dokkaebi.domain.studyroom.chat.ChatDto;
import com.dokkaebi.repository.StudyRoom.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService{

	@Autowired
	private ChatRepository chatRepository;
	
	@Override
	public boolean registMessage(ChatDto chatDto) {
		Chat chat=new Chat();
		chat.setMessage(chatDto.getMessage());
		chat.setSenderNickName(chatDto.getSenderNickName());
		chat.setTime(chatDto.getTime());
		
		Member member=new Member();
		member.setId(chatDto.getSenderId());
		
		StudyRoom studyRoom=new StudyRoom();
		studyRoom.setId(chatDto.getRoomId());

		chat.setMember(member);
		chat.setStudyRoom(studyRoom);
		chatRepository.save(chat);
		return true;
	}

	@Override
	public List<ChatDto> getStudyRoomChats(Long roomId) {
		List<Chat> chatList=chatRepository.findAllByStudyRoomId(roomId);
		List<ChatDto> chatDtoList=new ArrayList<>();
		for(Chat chat:chatList) {
			chatDtoList.add(new ChatDto(chat));
		}
		return chatDtoList;
	}



}
