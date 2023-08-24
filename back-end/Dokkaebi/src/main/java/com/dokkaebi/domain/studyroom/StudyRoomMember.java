package com.dokkaebi.domain.studyroom;

import java.time.LocalTime;

import com.dokkaebi.domain.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class StudyRoomMember {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="channel_id")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="room_id")
	private StudyRoom studyRoom;
	
	private int host; // 방장 여부: 1일경우 방장
	
	private int isActive; // 유저가 현재 스터디룸에 참여하는지 여부
	
	private int time;
	
	private LocalTime startTime;
}
