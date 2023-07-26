package com.dokkaebi.domain.community;

import lombok.Data;

@Data
public class FriendDTO {
  private Long id;
  private Long member1Id;
  private String member1Nickname;
  private Long member2Id;
  private String member2Nickname;

  public FriendDTO(Friend friend) {
    id = friend.getId();
    member1Id = friend.getMember1().getId();
    member1Nickname = friend.getMember1().getNickname();
    member2Id = friend.getMember2().getId();
    member2Nickname = friend.getMember2().getNickname();
  }
}
