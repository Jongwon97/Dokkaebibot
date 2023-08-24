package com.dokkaebi.repository.community;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Message;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MessageRepository {

  private final EntityManager entityManager;

  public void save(Message message) {
    entityManager.persist(message);
  }

  public List<Message> findReceived(Long memberId) {
    String query = "SELECT m FROM Message m "
        + "WHERE m.receiver.id = :id "
        + "ORDER BY m.createdAt DESC";
    return  entityManager.createQuery(query, Message.class)
        .setParameter("id", memberId)
        .getResultList();
  }
  public List<Message> findSent(Long memberId) {
    String query = "SELECT m FROM Message m "
        + "WHERE m.sender.id = :id "
        + "ORDER BY m.createdAt DESC";
    return  entityManager.createQuery(query, Message.class)
        .setParameter("id", memberId)
        .getResultList();
  }

  public List<Message> findAllByMemberId(Long memberId) {
    String query = "SELECT m FROM Message m "
        + "WHERE m.sender.id = :id "
        + "OR m.receiver.id = :id "
        + "ORDER BY m.createdAt";
    return entityManager.createQuery(query, Message.class)
        .setParameter("id", memberId)
        .getResultList();

  }

  public Optional<Message> findOne(Long messageId) {
    Message message = entityManager.find(Message.class, messageId);
    return Optional.ofNullable(message);
  }



  public void deleteOne(Message message) {
    entityManager.remove(message);
  }

}
