package com.dokkaebi.domain.studyroom.chat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChatDto {

	private Long roomId;			
	private Long senderId;			
	private String senderNickName;	// 보낸 유저 이름
	private String message;			// 메세지
	private String time;			// 채팅 발송 시간
	
	public ChatDto(Chat chat) {
		this.roomId = chat.getStudyRoom().getId();
		this.senderId = chat.getMember().getId();
		this.senderNickName = chat.getSenderNickName();
		this.message = chat.getMessage();
		this.time = chat.getTime();
	}

	public ChatDto(Long roomId, Long senderId, String senderNickName, String message, String time) {
		this.roomId = roomId;
		this.senderId = senderId;
		this.senderNickName = senderNickName;
		this.message = message;
		this.time = time;
	}
	
}
