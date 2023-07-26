package com.dokkaebi.domain.community;

import java.util.Date;
import lombok.Data;

@Data
public class CommentDTO {
  private Long id;
  private String content;
  private Date createdAt;
  private String writerNickname;
  private Long writerId;

  public CommentDTO(Comment comment) {
    id = comment.getId();
    content = comment.getContent();
    createdAt = comment.getCreatedAt();
    writerNickname = comment.getWriter().getNickname();
    writerId = comment.getWriter().getId();
  }

}
