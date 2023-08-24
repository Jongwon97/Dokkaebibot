package com.dokkaebi.repository;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberAnalysis;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberAnalysisRepository {
  private final EntityManager entityManager;

  public void save(MemberAnalysis memberAnalysis) {
    entityManager.persist(memberAnalysis);
  }

  public Optional<MemberAnalysis> findByMember(Member member) {
    String query = "SELECT ma FROM MemberAnalysis ma "
        + "WHERE ma.member.id = :memberId";
    return entityManager.createQuery(query, MemberAnalysis.class)
        .setParameter("memberId", member.getId())
        .getResultList()
        .stream().findFirst();
  }

  public MemberAnalysis getTotalRanker() {
    String query = "SELECT ma FROM MemberAnalysis ma "
        + "ORDER BY ma.timeTotalStudy DESC";
    return entityManager.createQuery(query, MemberAnalysis.class)
        .getResultList()
        .stream().findFirst().get();
  }

  public MemberAnalysis getNetRanker() {
    String query = "SELECT ma FROM MemberAnalysis ma "
        + "ORDER BY ma.timeNetStudy DESC";
    return entityManager.createQuery(query, MemberAnalysis.class)
        .getResultList()
        .stream().findFirst().get();
  }

  public MemberAnalysis getPoseRanker() {
    String query = "SELECT ma FROM MemberAnalysis ma "
        + "ORDER BY ma.timeGoodPose DESC";
    return entityManager.createQuery(query, MemberAnalysis.class)
        .getResultList()
        .stream().findFirst().get();
  }
}
