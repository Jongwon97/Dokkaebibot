package com.dokkaebi.repository.community;

import com.dokkaebi.domain.community.Like;
import com.dokkaebi.domain.community.Scrap;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeScrapRepository {

  private final EntityManager entityManager;

  public Optional<Like> findOneLike(Long articleId, Long memberId) {
    String query = "SELECT l FROM Like l "
        + "WHERE l.article.id = :articleId "
        + "AND l.member.id = :memberId";
    return entityManager
        .createQuery(query, Like.class)
        .setParameter("articleId", articleId)
        .setParameter("memberId", memberId)
        .getResultList().stream().findAny();
  }
  public Optional<Scrap> findOneScrap(Long articleId, Long memberId) {
    String query = "SELECT s FROM Scrap s "
        + "WHERE s.article.id = :articleId "
        + "AND s.member.id = :memberId";
    return entityManager
        .createQuery(query, Scrap.class)
        .setParameter("articleId", articleId)
        .setParameter("memberId", memberId)
        .getResultList().stream().findAny();
  }

  public void deleteOneLike(Like like) {
    entityManager.remove(like);
  }

  public void saveOneLike(Like like) {
    entityManager.persist(like);
  }
  public void deleteOneScrap(Scrap scrap) {
    entityManager.remove(scrap);
  }

  public void saveOneScrap(Scrap scrap) {
    entityManager.persist(scrap);
  }
}
