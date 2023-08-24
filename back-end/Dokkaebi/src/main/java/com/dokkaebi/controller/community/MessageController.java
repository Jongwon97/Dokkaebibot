package com.dokkaebi.controller.community;

import com.dokkaebi.domain.community.MessageReponseDTO;
import com.dokkaebi.domain.community.MessageRequestDTO;
import com.dokkaebi.exception.NotAuthorizedException;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.service.community.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/community/message")
@RequiredArgsConstructor
public class MessageController {

  private final MessageService messageService;

  // send message
  @PostMapping("/check")
  public ResponseEntity<?> sendMessage(
      @RequestBody MessageRequestDTO messageRequestDTO) {

    try {
      messageService.create(messageRequestDTO);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }

  // find All received message
  @GetMapping("received/check")
  public ResponseEntity<?> getAllReceivedMessage(
      HttpServletRequest httpServletRequest
  ) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    return new ResponseEntity<>(messageService.findReceived(memberId), HttpStatus.OK);
  }

  @GetMapping("received/{friendId}/check")
  public ResponseEntity<?> getAndReadMessageFromFriend(
      @PathVariable("friendId") Long friendId,
      HttpServletRequest httpServletRequest
  ) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    return new ResponseEntity<>(messageService.readMessageFromFriend(memberId, friendId), HttpStatus.OK);
  }



  // find All sent message
  @GetMapping("sent/check")
  public ResponseEntity<?> getAllSentMessage(
      HttpServletRequest httpServletRequest
  ) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    return new ResponseEntity<>(messageService.findSent(memberId), HttpStatus.OK);
  }

  // delete message
  @DeleteMapping("/{messageId}/check")
  public ResponseEntity<?> deleteMessage(
      @PathVariable Long messageId,
      HttpServletRequest httpServletRequest
  ) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      messageService.deleteOne(messageId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException | NotAuthorizedException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }

}
