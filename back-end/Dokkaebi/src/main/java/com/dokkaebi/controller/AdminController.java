package com.dokkaebi.controller;

import com.dokkaebi.domain.admin.Notice;
import com.dokkaebi.domain.admin.Qna;
import com.dokkaebi.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
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
@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

  public final AdminService adminService;

  @PostMapping("/notice/check")
  public ResponseEntity<?> postNotice(
      HttpServletRequest httpServletRequest,
      @RequestBody Notice notice) {

    Long noticeID = adminService.postNotice(notice);
    return new ResponseEntity<>(noticeID, HttpStatus.OK);
  }

  @PutMapping("/notice/check")
  public ResponseEntity<?> updateNotice(
      @RequestBody Notice notice
  ) {
    Long noticeId = adminService.updateNotice(notice);
    return new ResponseEntity<>(noticeId, HttpStatus.OK);
  }

  @GetMapping("/notice/all")
  public ResponseEntity<?> getNoticeList() {
    return new ResponseEntity<>(adminService.getNoticeList(), HttpStatus.OK);
  }

  @GetMapping("/notice/{id}")
  public ResponseEntity<?> getNoticeDetail(
      @PathVariable("id") Long noticeId
  ) {
    return new ResponseEntity<>(adminService.getNoticeDetail(noticeId), HttpStatus.OK);
  }

  @DeleteMapping("/notice/{id}/check")
  public ResponseEntity<?> deleteNotice(
      @PathVariable("id") Long noticeId
  ) {

    adminService.deleteNotice(noticeId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/qna/all")
  public ResponseEntity<?> getQnaList() {
    return new ResponseEntity<>(adminService.getQnaList(), HttpStatus.OK);
  }

  @GetMapping("/qna/{id}")
  public ResponseEntity<?> getQnaDetail(
      @PathVariable("id") Long qnaId
  ) {
    return new ResponseEntity<>(adminService.getQnaDetail(qnaId), HttpStatus.OK);
  }

  @PostMapping("/qna/check")
  public ResponseEntity<?> postQna(
      HttpServletRequest httpServletRequest,
      @RequestBody Qna qna) {

    Long memberId = (Long) httpServletRequest.getAttribute("id");
    Long qnaId = adminService.postQna(qna, memberId);
    return new ResponseEntity<>(qnaId, HttpStatus.OK);
  }

  @DeleteMapping("/qna/{id}/check")
  public ResponseEntity<?> deleteQna(
      @PathVariable("id") Long qnaId
  ) {

    adminService.deleteQna(qnaId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
