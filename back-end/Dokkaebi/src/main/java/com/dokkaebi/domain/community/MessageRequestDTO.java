package com.dokkaebi.domain.community;

import lombok.Data;

@Data
public class MessageRequestDTO {
  private String content;
  private Long senderId;
  private Long receiverId;
}
