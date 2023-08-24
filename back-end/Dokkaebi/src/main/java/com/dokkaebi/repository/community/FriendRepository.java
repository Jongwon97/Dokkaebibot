package com.dokkaebi.repository.community;

import com.dokkaebi.domain.community.Friend;
import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendRepository {

  private final EntityManager entityManager;

  public void save(Friend friend) {
    entityManager.persist(friend);
  }

  public void delete(Long id1, Long id2) {
    String query = "SELECT f FROM Friend f "
        + "WHERE f.member1.id = :id1 "
        + "AND f.member2.id = :id2";
    List<Friend> foundList = entityManager.createQuery(query, Friend.class)
        .setParameter("id1", id1)
        .setParameter("id2", id2)
        .getResultList();
    if (!foundList.isEmpty()) {
      foundList.forEach((f)->{entityManager.remove(f);});
    }
  }

  public List<Friend> findAll(Long memberId) {
    String query = "SELECT f FROM Friend f "
        + "WHERE f.member1.id = :id "
        + "OR f.member2.id = :id";
    return entityManager.createQuery(query, Friend.class)
        .setParameter("id", memberId)
        .getResultList();
  }

}
