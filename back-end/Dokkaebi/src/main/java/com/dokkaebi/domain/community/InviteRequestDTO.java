package com.dokkaebi.domain.community;

import lombok.Data;

@Data
public class InviteRequestDTO {
  private Long senderId;
  private Long receiverId;
  private Long studyRoomId;
}
