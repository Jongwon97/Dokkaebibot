package com.dokkaebi.repository;

import com.dokkaebi.domain.community.Article;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepository {
  private final EntityManager entityManager;

  public void save(Article article) {
    entityManager.persist(article);
  }
}
