package com.dokkaebi.domain.community;

import static java.util.stream.Collectors.toList;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Friend;
import com.dokkaebi.repository.MemberRepository;
import com.dokkaebi.repository.community.InviteRepository;
import com.dokkaebi.repository.community.MessageRepository;
import java.util.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
public class FriendDTO {

  private Long id;
  private Long friendId;
  private String friendEmail;
  private String friendNickname;
  private Integer friendIconNumber;
  private Boolean isNewMessage;

  public FriendDTO(Friend f, Member friend) {
    id = f.getId();
    friendId = friend.getId();
    friendEmail = friend.getEmail();
    friendNickname = friend.getNickname();
    friendIconNumber = friend.getIconNumber();
  }
}
