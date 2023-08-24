package com.dokkaebi.domain.community;

import com.dokkaebi.service.TimeService;
import lombok.Data;

@Data
public class ArticleSimpleDTO {

  private Long id;
  private String title;
  private String content;
  private String createdAt;
  private String writerNickname;
  private int numLike;
  private int numScrap;

  public ArticleSimpleDTO(Article article) {
    id = article.getId();
    title = article.getTitle();
    content = article.getContent();
    createdAt = TimeService.toDate(article.getCreatedAt());
    writerNickname = article.getWriter().getNickname();
    numLike = article.getLikeList().size();
    numScrap = article.getScrapList().size();
  }
}
