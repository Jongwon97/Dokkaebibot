package com.dokkaebi.domain.studyroom;

import java.util.Date;
import lombok.Data;

@Data
public class SocketStudyRoomMemberDto {

	private String type;	// 	전송 목적 구별
	private Long memberId;		// 	멤버 아이디
	private Long roomId;
	private int isActive;	// 	현재 스터디룸 활동 여부

	private	int time;
	private int iconNumber;		//	Iot 데이터
	private int defaultIcon;
	/*
	  	0: default
	  	1: phone
	  	2. sleep
	  	3. 자리비움
	  	4. 공부
	  	5. 자세나쁨
	 */	

	public SocketStudyRoomMemberDto(Long mId, Long rId, Integer dIcon, String payload) {
		type = "state";
		memberId = mId;
		roomId = rId;
		isActive = 1;
		defaultIcon = dIcon;
		switch (payload) {
			case "ON, OFF" -> {
				iconNumber = 0;
			}
			case "phone" -> {
				iconNumber = 1;
			}
			case "sleep" -> {
				iconNumber = 2;
			}
			case "away" -> {
				iconNumber = 3;
			}
			case "study" -> {
				iconNumber = 4;
			}
			case "good" -> {
				iconNumber = 4;
			}
			case "bad" -> {
				iconNumber = 5;
			}
		}
	}

	public SocketStudyRoomMemberDto(Long mId, Integer dIcon, Long rId) {
		type = "start";
		memberId = mId;
		roomId = rId;
		isActive = 1;
		defaultIcon = dIcon;
		iconNumber = 4;
	}
	
	public SocketStudyRoomMemberDto(Long memberId, Long roomId, Integer dIcon, int time) {
		this.type = "timeEnd";
		this.memberId = memberId;
		this.roomId = roomId;
		this.isActive = 0;
		this.defaultIcon = dIcon;
		this.time=time;
		this.iconNumber = 0;
	}
	
	public SocketStudyRoomMemberDto() {

	}
	
}
