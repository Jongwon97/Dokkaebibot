package com.dokkaebi.repository.community;

import com.dokkaebi.domain.community.Invite;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InviteRepository {

  private final EntityManager entityManager;
  public void save(Invite invite) {
    entityManager.persist(invite);
  }

  public Optional<Invite> findOne(Long inviteId) {
    return Optional.ofNullable(entityManager.find(Invite.class, inviteId));
  }

  public void deleteOne(Invite invite) {
    entityManager.remove(invite);
  }

  public List<Invite> findSent(Long memberId) {
    String query = "SELECT i FROM Invite i "
        + "WHERE i.sender.id = :id "
        + "ORDER BY i.createdAt DESC";
    return entityManager.createQuery(query, Invite.class)
        .setParameter("id", memberId)
        .getResultList();
  }

  public List<Invite> findReceived(Long memberId) {
    String query = "SELECT i FROM Invite i "
        + "WHERE i.receiver.id = :id "
        + "ORDER BY i.createdAt DESC";
    return entityManager.createQuery(query, Invite.class)
        .setParameter("id", memberId)
        .getResultList();
  }
}
