package com.dokkaebi.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class StudyDataDTO {

  List<String> timeIndex;
  List<Float> temperatureData;
  List<Float> dustData;
  List<Float> humidityData;

  List<Long> poseTimeIndex;
  List<String> poseData;
  Map<String, Long> poseTimeSum;

  Long start;
  Long end;

  public StudyDataDTO() {
    timeIndex = new ArrayList<>();
    temperatureData = new ArrayList<>();
    dustData = new ArrayList<>();
    humidityData = new ArrayList<>();

    poseTimeIndex = new ArrayList<>();
    poseData = new ArrayList<>();
    poseTimeSum = new HashMap<>();
    poseTimeSum.put("good", 0L);
    poseTimeSum.put("bad", 0L);
    poseTimeSum.put("away", 0L);
    poseTimeSum.put("sleep", 0L);
    poseTimeSum.put("phone", 0L);
  }
}
