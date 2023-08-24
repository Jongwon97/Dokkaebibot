package com.dokkaebi.domain;

import com.dokkaebi.domain.studyroom.StudyRoom;

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
public class MemberInterest {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="interest_id")
	private Long id;
	
	private String tagName;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
}
