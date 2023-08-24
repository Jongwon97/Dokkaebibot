package com.dokkaebi.controller.community;

import com.dokkaebi.domain.community.InviteRequestDTO;
import com.dokkaebi.exception.NotAuthorizedException;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.service.community.InviteService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
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
@RequestMapping("/api/community/invite")
@RequiredArgsConstructor
public class InviteController {

  private final InviteService inviteService;

  // send friend or studyRoom invite
  @PostMapping("/check")
  public ResponseEntity<?> postInvite(
      @RequestBody InviteRequestDTO inviteRequestDTO) {

    try {
      inviteService.create(inviteRequestDTO);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }

  // delete invite (when declined)
  @DeleteMapping("/{inviteId}/check")
  public ResponseEntity<?> deleteInvite(
      @PathVariable Long inviteId,
      HttpServletRequest httpServletRequest) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      inviteService.deleteOne(inviteId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException | NotAuthorizedException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }

  // read invite
  @GetMapping("/sent/check")
  public ResponseEntity<?> getAllSentInvite(
      HttpServletRequest httpServletRequest) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    return new ResponseEntity<>(
        inviteService.findSent(memberId)
        , HttpStatus.OK);
  }
  @GetMapping("/received/check")
  public ResponseEntity<?> getAllReceivedInvite(
      HttpServletRequest httpServletRequest) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    return new ResponseEntity<>(
        inviteService.findReceived(memberId)
        , HttpStatus.OK);
  }

}
