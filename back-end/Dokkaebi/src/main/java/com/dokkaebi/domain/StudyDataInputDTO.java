package com.dokkaebi.domain;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class StudyDataInputDTO {

  private String serialNumber;
  private Long start;
  private Long end;
  private List<CameraData> camera;
  private List<AtmosphereData> atmosphere;

  @Data
  public static class CameraData {
    public String status;
    public Long time;
  }

  @Data
  public static class AtmosphereData {
    public Float temperature;
    public Float dust;
    public Float humidity;
    public Long time;
  }

}
