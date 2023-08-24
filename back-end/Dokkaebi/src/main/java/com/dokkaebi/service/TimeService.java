package com.dokkaebi.service;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeService {

  public static final int SEC = 60;
  public static final int MIN = 60;
  public static final int HOUR = 24;
  public static final int DAY = 30;
  public static final int WEEK = 7;
  public static final int MONTH = 12;

  public static String toDate(Date date) {
    long diffTime = (System.currentTimeMillis() - date.getTime()) / 1000;
    if (diffTime < SEC){
      return diffTime + "초전";
    } else if (diffTime / SEC < MIN) {
      return (diffTime / SEC) + "분 전";
    } else if (diffTime / SEC / MIN < HOUR) {
      return (diffTime / SEC / MIN) + "시간 전";
    } else if (diffTime / SEC / MIN / HOUR < DAY) {
      return (diffTime / SEC / MIN / HOUR) + "일 전";
    } else if (diffTime / SEC/ MIN / HOUR / DAY < WEEK) {
      return (diffTime / SEC / MIN / HOUR / DAY) + "주 전";
    } else if (diffTime / SEC / MIN / HOUR / DAY / WEEK < MONTH) {
      return (diffTime / SEC / MIN / HOUR / DAY / WEEK) + "개월 전";
    } else {
      return (diffTime / SEC / MIN / HOUR / DAY / WEEK / MONTH) + "년 전";
    }
  }
}
