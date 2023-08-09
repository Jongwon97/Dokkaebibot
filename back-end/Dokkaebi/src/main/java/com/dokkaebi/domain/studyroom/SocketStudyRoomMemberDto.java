package com.dokkaebi.domain.studyroom;

import lombok.Data;

@Data
public class SocketStudyRoomMemberDto {

	private String type;	// 	전송 목적 구별
	private Long memberId;		// 	멤버 아이디
	private Long roomId;
	private int isActive;	// 	현재 스터디룸 활동 여부
	private int state;		//	Iot 데이터
	
}
