package com.dokkaebi.service.community;

import static java.util.stream.Collectors.toList;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Friend;
import com.dokkaebi.domain.community.FriendDTO;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.repository.MemberRepository;
import com.dokkaebi.repository.community.FriendRepository;
import com.dokkaebi.repository.community.MessageRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {

  private final MemberRepository memberRepository;
  private final FriendRepository friendRepository;
  private final MessageRepository messageRepository;

  public void create(Long friendId, Long memberId) throws NotRegisteredException{
    Optional<Member> optionalMember = memberRepository.findById(memberId);
    Optional<Member> optionalFriendMember = memberRepository.findById(friendId);
    optionalFriendMember.orElseThrow(() -> new NotRegisteredException("friend"));

    // do nothing if memberId == friendId
    if (memberId.equals(friendId)) { return; }

    Friend friend = new Friend();
    friend.setMember1( // put member with smaller id first at member1
        memberId < friendId ? optionalMember.get() : optionalFriendMember.get()
    );
    friend.setMember2( // then bigger one at member2
        memberId > friendId ? optionalMember.get() : optionalFriendMember.get()
    );
    friendRepository.save(friend);
  }

  public void delete(Long friendId, Long memberId) {
    Optional<Member> optionalMember = memberRepository.findById(memberId);
    Optional<Member> optionalFriendMember = memberRepository.findById(friendId);
    optionalFriendMember.orElseThrow(() -> new NotRegisteredException("friend"));

    // do nothing if memberId == friendId
    if (memberId.equals(friendId)) { return; }

    friendRepository.delete(
        memberId < friendId ? memberId : friendId,
        memberId > friendId ? memberId : friendId
    );
  }

  public List<FriendDTO> findAll(Long memberId) {
    List<Friend> friendList = friendRepository.findAll(memberId);
    return friendList.stream().map((f)->{
      Member friend = memberId.equals(f.getMember1().getId()) ? f.getMember2() : f.getMember1();
      FriendDTO friendDTO = new FriendDTO(f, friend);
      boolean isNewMessage = messageRepository.findSent(friend.getId()).stream()
        .anyMatch(message -> {
          if (message.getReceiver().getId().equals(memberId)) {
            return message.isNotRead();
          }
          return false;
        });
      friendDTO.setIsNewMessage(isNewMessage);
      return friendDTO;
    }).collect(toList());
  }
}
