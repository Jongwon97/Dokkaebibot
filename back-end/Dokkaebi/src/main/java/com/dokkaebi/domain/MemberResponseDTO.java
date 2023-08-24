package com.dokkaebi.domain;

import lombok.Data;

@Data
public class MemberResponseDTO {
  private Long id;
  private String email;
  private String nickname;
  private Integer iconNumber;
  private Boolean haveDevice;
  private Boolean isAdmin;
  private String info;
  
  public MemberResponseDTO(Member member) {
    id = member.getId();
    email = member.getEmail();
    nickname = member.getNickname();
    iconNumber = member.getIconNumber();
    haveDevice = (member.getDevice() != null);
    isAdmin = member.isAdmin();
    info = member.getIntroduction();
  }
}
