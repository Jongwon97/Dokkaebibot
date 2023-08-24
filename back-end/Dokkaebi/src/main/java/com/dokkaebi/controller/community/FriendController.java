package com.dokkaebi.controller.community;

import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.service.community.FriendService;
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
@RequiredArgsConstructor
@RequestMapping("/api/community/friend")
public class FriendController {

  private final FriendService friendService;

  @PostMapping("/{friendId}/check")
  public ResponseEntity<?> createFriend(
      @PathVariable Long friendId,
      HttpServletRequest httpServletRequest
  ) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      friendService.create(friendId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }

  @GetMapping("/check")
  public ResponseEntity<?> findFriend(
      HttpServletRequest httpServletRequest
  ) {
    Long memberId = (Long) httpServletRequest.getAttribute("id");
    return new ResponseEntity<>(friendService.findAll(memberId), HttpStatus.OK);
  }

  @DeleteMapping("/{friendId}/check")
  public ResponseEntity<?> deleteFriend(
      @PathVariable Long friendId,
      HttpServletRequest httpServletRequest
  ) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      friendService.delete(friendId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }
}
