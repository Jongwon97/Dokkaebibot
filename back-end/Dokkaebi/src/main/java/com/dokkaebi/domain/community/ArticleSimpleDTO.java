package com.dokkaebi.domain.community;

import java.util.Date;
import lombok.Data;

@Data
public class ArticleSimpleDTO {
  private Long id;
  private String title;
  private Date createdAt;
  private int numLike;
  private int numScrap;

  public ArticleSimpleDTO(Article article) {
    id = article.getId();
    title = article.getTitle();
    createdAt = article.getCreatedAt();
    numLike = article.getLikeList().size();
    numScrap = article.getScrapList().size();
  }
}
