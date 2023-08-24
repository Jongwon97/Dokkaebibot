package com.dokkaebi.controller.community;

import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.service.community.LikeScrapService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/community")
@RequiredArgsConstructor
public class LikeScrapController {

  private final LikeScrapService likeScrapService;

  @PostMapping("/like/{articleId}/check")
  public ResponseEntity<?> toggleLike(
      @PathVariable Long articleId,
      HttpServletRequest httpServletRequest) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      likeScrapService.toggleLike(articleId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception.toString());
    }
  }

  @PostMapping("/scrap/{articleId}/check")
  public ResponseEntity<?> toggleScrap(
      @PathVariable Long articleId,
      HttpServletRequest httpServletRequest) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      likeScrapService.toggleScrap(articleId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception.toString());
    }
  }
//  @PostMapping("/like/{articleId}/check")
//  public ResponseEntity<?> postLike(
//      @PathVariable Long articleId,
//      HttpServletRequest httpServletRequest) {
//
//    Long memberId = (Long) httpServletRequest.getAttribute("id");
//    try {
//      likeScrapService.postLike(articleId, memberId);
//      return new ResponseEntity<>(HttpStatus.OK);
//    } catch (NotRegisteredException exception) {
//      return ResponseEntity.badRequest().body(exception.toString());
//    }
//  }
//
//  @DeleteMapping("/like/{articleId}/check")
//  public ResponseEntity<?> deleteLike(
//      @PathVariable Long articleId,
//      HttpServletRequest httpServletRequest) {
//
//    Long memberId = (Long) httpServletRequest.getAttribute("id");
//    try {
//      likeScrapService.deleteLike(articleId, memberId);
//      return new ResponseEntity<>(HttpStatus.OK);
//    } catch (NotRegisteredException exception) {
//      return ResponseEntity.badRequest().body(exception.toString());
//    }
//  }
//
//  @PostMapping("/scrap/{articleId}/check")
//  public ResponseEntity<?> postScrap(
//      @PathVariable Long articleId,
//      HttpServletRequest httpServletRequest) {
//
//    Long memberId = (Long) httpServletRequest.getAttribute("id");
//    try {
//      likeScrapService.postScrap(articleId, memberId);
//      return new ResponseEntity<>(HttpStatus.OK);
//    } catch (NotRegisteredException exception) {
//      return ResponseEntity.badRequest().body(exception);
//    }
//  }
//  @DeleteMapping("/scrap/{articleId}/check")
//  public ResponseEntity<?> deleteScrap(
//      @PathVariable Long articleId,
//      HttpServletRequest httpServletRequest) {
//
//    Long memberId = (Long) httpServletRequest.getAttribute("id");
//    try {
//      likeScrapService.deleteScrap(articleId, memberId);
//      return new ResponseEntity<>(HttpStatus.OK);
//    } catch (NotRegisteredException exception) {
//      return ResponseEntity.badRequest().body(exception);
//    }
//  }


}
