package com.dokkaebi.repository;

import com.dokkaebi.domain.StudyData;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StudyDataRepository {

  private final EntityManager entityManager;

  public List<StudyData> findDataFromTo(Date from, Date to, Long memberId) {
    String query = "SELECT sd FROM StudyData sd "
        + "WHERE sd.member.id = :memberId "
        + "AND sd.start >= :from "
        + "AND sd.start < :to";
    return entityManager.createQuery(query, StudyData.class)
        .setParameter("from", from)
        .setParameter("to", to)
        .setParameter("memberId", memberId)
        .getResultList();
  }
}
