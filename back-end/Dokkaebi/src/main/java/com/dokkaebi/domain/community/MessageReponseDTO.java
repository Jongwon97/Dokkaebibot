package com.dokkaebi.domain.community;

import com.dokkaebi.service.TimeService;
import lombok.Data;

@Data
public class MessageReponseDTO {
  private Long id;
  private String content;
  private Long senderId;
  private String senderNickname;
  private Integer senderIconNumber;
  private Long receiverId;
  private String receiverNickname;
  private Integer receiverIconNumber;
  private String createdAt;

  public MessageReponseDTO(Message message) {
    id = message.getId();
    content = message.getContent();
    senderId = message.getSender().getId();
    senderNickname = message.getSender().getNickname();
    senderIconNumber = message.getSender().getIconNumber();
    receiverId = message.getReceiver().getId();
    receiverNickname = message.getReceiver().getNickname();
    receiverIconNumber = message.getSender().getIconNumber();
    createdAt = TimeService.toDate(message.getCreatedAt());
  }

}
