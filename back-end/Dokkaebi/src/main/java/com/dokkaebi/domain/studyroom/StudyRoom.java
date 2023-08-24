package com.dokkaebi.domain.studyroom;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dokkaebi.domain.community.Invite;
import com.dokkaebi.domain.studyroom.chat.Chat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class StudyRoom {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="room_id")
	private Long id;
	
	private String title;
	
	 @Temporal(TemporalType.TIMESTAMP) 
	 private Date createdDate;
	 
	 private int lockStatus; 		// 스터디방 잠금 여부 -> 1일 경우 잠겨 있음
	 
	 private String password;		// 패스워드
	 
	 private int maxCapacity;		// 최대 참여 가능 인원
	 
	 private int curAttendance; 	// 현재 스터디방 참여 인원
	 
	 private String headerImg;
	 

	 // 영속성 전이
	 @OneToMany(mappedBy="studyRoom", cascade=CascadeType.ALL)
	 List<HashTag> hashTags=new ArrayList<>(); // 해쉬 태그
	 
	 @OneToMany(mappedBy="studyRoom", cascade=CascadeType.ALL)
	 List<StudyRoomMember> members=new ArrayList<>();	// 멤버

	@OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL)
	List<Invite> inviteList = new ArrayList<>();
	
	@OneToMany(mappedBy = "studyRoom", cascade = CascadeType.ALL)
	List<Chat> chatList = new ArrayList<>();
	 
}
