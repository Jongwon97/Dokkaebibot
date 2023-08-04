package com.dokkaebi.domain.studyroom.chat;

import lombok.Data;

@Data
public class ChatDto {

	private Long roomId;
	private Long memberId;
	private String sender;	// 보낸 유저 이름
	private String message;	// 메세지
	private String time;	// 채팅 발송 시간
}
