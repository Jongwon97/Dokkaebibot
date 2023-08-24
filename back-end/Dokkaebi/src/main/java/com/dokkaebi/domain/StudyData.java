package com.dokkaebi.domain;

//import com.dokkaebi.mqtt.MapToJsonConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.Any;

@Entity
@Table(name = "study_data")
@Getter @Setter
public class StudyData {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "study_data_id")
  private Long id;

  private Date start;
  private Date end;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

//  @Column(columnDefinition = "json")
//  @Convert(attributeName = "data", converter = MapToJsonConverter.class)
//  private Map<String, Any> data = new HashMap<>();

  @Column(length = 10000)
  private String data;
}
