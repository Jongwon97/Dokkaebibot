package com.dokkaebi.domain.studyroom;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberResponseDTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// StudyRoomMember를 반환하기 위한 Dto
@Data
public class StudyRoomMemberDto {
	  private Long id;	// 멤버 아이디
	  private String nickname;
	  private int isActive;
	  private Integer iconNumber;
		private Integer defaultIcon;
	  private int host;
	  private int time;
	  
	  public StudyRoomMemberDto(Member member, int host, int isActive, int time) {
	    this.id = member.getId();
	    this.nickname = member.getNickname();
	    this.iconNumber = member.getIconNumber();
			this.defaultIcon = member.getIconNumber();
	    this.host=host;
	    this.isActive=isActive;
	    this.time=time;
	  }
}
