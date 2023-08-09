package com.dokkaebi.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class StudyStatusDTO {

  private Long memberId;
  private Map<String, Map<String, Float>> atmosphere;
  private String study;

  public void setAtmosphere(Map<String, Float> atmosphereStatus) {
    atmosphere.put("atmosphere", atmosphereStatus);
  }
}
