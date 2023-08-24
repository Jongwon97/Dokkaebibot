package com.dokkaebi.repository.community;

import com.dokkaebi.domain.community.Article;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleRepository implements CommunityRepository {
  private final EntityManager entityManager;

  public Long save(Article article) {
    entityManager.persist(article);
    return article.getId();
  }

  public List<Article> findAll() {
    return entityManager.createQuery("SELECT a FROM Article a", Article.class)
        .setMaxResults(100) // ???
        .getResultList();
  }

  public List<Article> findPopular(int start, int num) {
    String query = "SELECT a FROM Article a "
        + "LEFT JOIN Like l ON a = l.article "
        + "GROUP BY a.id "
        + "ORDER BY COUNT(l.id) DESC";
    return entityManager
        .createQuery(query, Article.class)
        .setFirstResult(start)
        .setMaxResults(num)
        .getResultList();
  }
  public List<Article> findNew(int start, int num) {
    String query = "SELECT a FROM Article a ORDER BY a.createdAt DESC";
    return entityManager
        .createQuery(query, Article.class)
        .setFirstResult(start)
        .setMaxResults(num)
        .getResultList();
  }

  @Override
  public Optional<Article> findOne(Long id) {
    return Optional.ofNullable(entityManager.find(Article.class, id));
  }

  public void deleteOne(Long id) {
    entityManager.remove(entityManager.find(Article.class, id));
  }

  public Long updateOne(Article article) {
    Article attached = entityManager.find(Article.class, article.getId());
    attached.setContent(article.getContent());
    attached.setTitle(article.getTitle());
//    entityManager.merge(article); -> makes empty field null
    return article.getId();
  }

  @Override
  public Long findMemberId(Long articleId) {
    return entityManager.find(Article.class, articleId).getWriter().getId();
  }

  public List<Article> findLikeTitle(String articleTitle) {
    String query = "SELECT a FROM Article a "
        + "WHERE a.title LIKE :title";

    return entityManager.createQuery(query, Article.class)
        .setParameter("title", articleTitle+"%")
        .getResultList();
  }
}
