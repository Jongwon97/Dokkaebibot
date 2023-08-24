package com.dokkaebi.mqtt;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.StudyData;
import com.dokkaebi.domain.StudyDataInputDTO;
import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Any;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MqttRepository {
  private final EntityManager entityManager;

  public void saveStudyData(Member member, StudyDataInputDTO data) {
    StudyData studyData = new StudyData();
    studyData.setMember(member);
    Gson gson = new Gson();
    studyData.setData(gson.toJson(data));

    Timestamp ts = new Timestamp(data.getStart());
    studyData.setStart(new Date(ts.getTime()));
    ts.setTime(data.getEnd());
    studyData.setEnd(new Date(ts.getTime()));
    entityManager.persist(studyData);
  }

}
