package com.dokkaebi.controller.community;

import com.dokkaebi.domain.community.Comment;
import com.dokkaebi.exception.NotAuthorizedException;
import com.dokkaebi.exception.NotRegisteredException;
import com.dokkaebi.service.community.CommentService;
import com.dokkaebi.service.community.CommunityExceptionService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/community/comment")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  // CREATE
  @PostMapping("/{articleId}/check")
  public ResponseEntity<?> createComment(
      @RequestBody Comment comment,
      @PathVariable Long articleId,
      HttpServletRequest httpServletRequest) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      commentService.create(comment, articleId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException | NotAuthorizedException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }

  // READ
  @GetMapping("/{articleId}")
  public ResponseEntity<?> getFromArticle(@PathVariable Long articleId) {
    return new ResponseEntity<>(commentService.getFromArticle(articleId), HttpStatus.OK);
  }

  // UPDATE
  @PutMapping("/{commentId}/check")
  public ResponseEntity<?> updateComment(
      @RequestBody Comment comment,
      @PathVariable Long commentId,
      HttpServletRequest httpServletRequest) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      commentService.updateOne(comment, commentId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotAuthorizedException | NotRegisteredException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }


  // DELETE
  @DeleteMapping("/{commentId}/check")
  public ResponseEntity<?> deleteComment(
      @PathVariable Long commentId,
      HttpServletRequest httpServletRequest) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      commentService.deleteOne(commentId, memberId);
      return new ResponseEntity<>(HttpStatus.OK);
    } catch (NotRegisteredException | NotAuthorizedException exception) {
      return ResponseEntity.badRequest().body(exception);
    }
  }
}
