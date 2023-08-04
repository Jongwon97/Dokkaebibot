package com.dokkaebi.domain.community;

import lombok.Data;

@Data
public class InviteRequestDTO {
  private Long senderId;
  private String receiverEmail;
  private Long studyRoomId;
}
