package com.dokkaebi.domain.community;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Article;
import com.dokkaebi.service.TimeService;
import java.util.Date;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class ArticleDTO {
  private Long id;
  private String title;
  private String content;
  private String createdAt;
  private Long writerId;
  private String writerNickname;
  private int numLike;
  private int numScrap;
  private boolean liked;
  private boolean scrapped;

  public ArticleDTO(Article article) {
    id = article.getId();
    title = article.getTitle();
    content = article.getContent();
    writerId = article.getWriter().getId();
    writerNickname = article.getWriter().getNickname();
    numLike = article.getLikeList().size();
    numScrap = article.getScrapList().size();
    liked = false;
    scrapped = false;
    createdAt = TimeService.toDate(article.getCreatedAt());
  }

}
