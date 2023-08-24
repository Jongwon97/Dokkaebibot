package com.dokkaebi.repository.community;

import com.dokkaebi.domain.community.Article;
import com.dokkaebi.domain.community.Comment;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository implements CommunityRepository {

  private final EntityManager entityManager;

  public void save(Comment comment) {
    entityManager.persist(comment);
  }

  @Override
  public Optional<Comment> findOne(Long id) {
    return Optional.ofNullable(entityManager.find(Comment.class, id));
  }

  public void updateOne(Comment comment, Long id) {
    Comment attached = entityManager.find(Comment.class, id);
    attached.setContent(comment.getContent());
  }

  public void deleteOne(Long id) {
    entityManager.remove(entityManager.find(Comment.class, id));
  }

  public List<Comment> findFromArticle(Long articleId) {
    Article article = entityManager.find(Article.class, articleId);
    if (article == null) {
      return new ArrayList<Comment>();
    }
    return article.getCommentList();
  }

  @Override
  public Long findMemberId(Long commentId) {
    return entityManager.find(Comment.class, commentId).getWriter().getId();
  }

}
