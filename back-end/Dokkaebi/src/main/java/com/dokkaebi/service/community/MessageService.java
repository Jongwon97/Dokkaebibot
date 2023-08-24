package com.dokkaebi.service.community;

import static java.util.stream.Collectors.toList;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Message;
import com.dokkaebi.domain.community.MessageReponseDTO;
import com.dokkaebi.domain.community.MessageRequestDTO;
import com.dokkaebi.exception.NotAuthorizedException;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.repository.MemberRepository;
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
public class MessageService {

  private final MessageRepository messageRepository;
  private final MemberRepository memberRepository;
  public void create(MessageRequestDTO messageRequestDTO) throws NotRegisteredException{
    Optional<Member> optionalSender = memberRepository.findById(messageRequestDTO.getSenderId());
    Optional<Member> optionalReceiver = memberRepository.findById(messageRequestDTO.getReceiverId());

    optionalSender.orElseThrow(() -> new NotRegisteredException(" SENDER"));
    optionalReceiver.orElseThrow(() -> new NotRegisteredException(" RECEIVER"));

    Message message = new Message();
    message.setContent(messageRequestDTO.getContent());
    message.setSender(optionalSender.get());
    message.setReceiver(optionalReceiver.get());
    messageRepository.save(message);
  }

  public List<MessageReponseDTO> findReceived(Long memberId) {
    List<Message> messageList = messageRepository.findReceived(memberId);
    return messageList.stream().map((m) -> {
      return new MessageReponseDTO(m);
    }).collect(toList());
  }

  public List<MessageReponseDTO> findSent(Long memberId) {
    List<Message> messageList = messageRepository.findSent(memberId);
    return messageList.stream().map((m) -> {
      return new MessageReponseDTO(m);
    }).collect(toList());
  }

  public void deleteOne(Long messageId, Long memberId) throws NotRegisteredException{
    Optional<Member> optionalMember = memberRepository.findById(memberId);
    Optional<Message> optionalMessage = messageRepository.findOne(messageId);

    optionalMember.orElseThrow(() -> new NotRegisteredException("member"));
    optionalMessage.orElseThrow(() -> new NotRegisteredException("message"));
    if (optionalMessage.get().getSender().getId().equals(memberId) ||
        optionalMessage.get().getReceiver().getId().equals(memberId)
    ) {
      messageRepository.deleteOne(optionalMessage.get());
    } else {
      throw new NotAuthorizedException();
    }
  }

  public List<MessageReponseDTO> readMessageFromFriend(Long memberId, Long friendId) {
    // find all memberId related messages
    List<Message> messageList = messageRepository.findAllByMemberId(memberId);
    return messageList.stream().filter(message -> {
      // get message with friendId
      if (message.getReceiver().getId().equals(friendId)) {
        return true;
      } else if (message.getSender().getId().equals(friendId)) {
        // check read if receiver is member, sender is friend
        message.setNotRead(false);
        return true;
      }
      return false;
    }).map(message -> {
      return new MessageReponseDTO(message);
    }).toList();
  }
}
