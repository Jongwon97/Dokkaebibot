package com.dokkaebi.controller;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberAnalysis;
import com.dokkaebi.domain.RankerDTO;
import com.dokkaebi.domain.StudyDataDTO;
import com.dokkaebi.repository.MemberAnalysisRepository;
import com.dokkaebi.repository.StudyDataRepository;
import com.dokkaebi.service.MemberAnalysisService;
import com.dokkaebi.service.StudyDataService;
import jakarta.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/analysis")
@RestController
@RequiredArgsConstructor
public class MemberAnalysisController {

  private final MemberAnalysisRepository memberAnalysisRepository;
  private final MemberAnalysisService memberAnalysisService;
  private final StudyDataService studyDataService;

  @GetMapping("/ranker")
  public ResponseEntity<?> getRanker() {
    try {
      RankerDTO total = memberAnalysisService.getTotalRanker();
      RankerDTO net = memberAnalysisService.getNetRanker();
      RankerDTO pose = memberAnalysisService.getPoseRanker();
      Map<String, Object> body = new HashMap<>();
      body.put("total", total);
      body.put("net", net);
      body.put("pose", pose);
      return new ResponseEntity<>(body, HttpStatus.OK);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

  @GetMapping("{start}/{end}/graph/check")
  public ResponseEntity<?> getGraphData(
      HttpServletRequest httpServletRequest,
      @PathVariable("start") String start,
      @PathVariable("end") String end) {
    // repository에서 해당 day에 맞는 데이터를 받고
    // service에서 하루 데이터로 정리하고 -> 누적 데이터로 만들어야 한다 -> data analysis 정리해야 할 듯
    Long memberId = (Long) httpServletRequest.getAttribute("id");
    try {
      StudyDataDTO studyDataDTO = studyDataService.getDataRange(start, end, memberId);
      // 여기서 프론트로 전송
      return new ResponseEntity<>(studyDataDTO, HttpStatus.OK);
    } catch (ParseException e) {
      return ResponseEntity.badRequest().body(e);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
  }

}
