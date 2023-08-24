package com.dokkaebi.domain.community;

import com.dokkaebi.domain.community.Invite;
import com.dokkaebi.service.TimeService;
import lombok.Data;

@Data
public class InviteResponseDTO {
  private Long id;

  // sender id
  private Long senderId;
  // sender nickname
  private String senderNickname;
  // studyRoom id
  private Long studyRoomId;
  // studyRoom title
  private String studyRoomTitle;

  private String createdAt;

  private boolean notRead;

  public InviteResponseDTO(Invite invite) {
    id = invite.getId();
    senderId = invite.getSender().getId();
    senderNickname = invite.getSender().getNickname();

    if (invite.getStudyRoom() != null) {
      studyRoomId = invite.getStudyRoom().getId();
      studyRoomTitle = invite.getStudyRoom().getTitle();
    }
    createdAt = TimeService.toDate(invite.getCreatedAt());
    notRead = invite.isNotRead();
  }
}
