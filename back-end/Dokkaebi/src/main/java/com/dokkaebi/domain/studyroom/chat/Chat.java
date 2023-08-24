package com.dokkaebi.domain.studyroom.chat;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.studyroom.StudyRoom;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Chat {
		
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="chat_id")
	private Long id;
	
	private String senderNickName;	// 보낸 유저 이름

	private String message;	// 메세지
	
	private String time;	// 채팅 발송 시간
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;			// Member

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="room_id")
	private StudyRoom studyRoom;	// studyRoom
	
}
