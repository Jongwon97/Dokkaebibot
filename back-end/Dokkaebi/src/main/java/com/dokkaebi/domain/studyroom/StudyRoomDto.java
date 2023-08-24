package com.dokkaebi.domain.studyroom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

// StudyRoom Entity 반환 DTO
@Data
public class StudyRoomDto {

	private Long room_id;
	
	private String title;
	
	 @Temporal(TemporalType.TIMESTAMP) 
	 private Date createdDate;
	 
	 private int lockStatus; 		// 스터디방 잠금 여부 -> 1일 경우 잠겨 있음
	 
	 private String password;		// 패스워드
	 
	 private int maxCapacity;		// 최대 참여 가능 인원
	 
	 private int curAttendance; 	// 현재 스터디방 참여 인원
	 
	 private String headerImg;
	 
	 List<String> tagNames=new ArrayList<>();
	 
	 public StudyRoomDto(StudyRoom studyRoom) {
		 this.room_id=studyRoom.getId();
		 this.title=studyRoom.getTitle();
		 this.createdDate=studyRoom.getCreatedDate();
		 this.lockStatus=studyRoom.getLockStatus();
		 this.password=studyRoom.getPassword();
		 this.maxCapacity=studyRoom.getMaxCapacity();
		 this.curAttendance=studyRoom.getCurAttendance();
		 this.headerImg=studyRoom.getHeaderImg();
		 for(int i=0;i<studyRoom.getHashTags().size();i++) {
			this.tagNames.add(studyRoom.getHashTags().get(i).getTagName());
		 }
	 }
}
