package com.dokkaebi.domain;

import lombok.Data;

@Data
public class RankerDTO {
  private String nickname;
  private Integer iconNumber;
  private String timeSum;

  public RankerDTO(Member member, String inputTimeSum) {
    nickname = member.getNickname();
    iconNumber = member.getIconNumber();
    timeSum = inputTimeSum;
  }
}
