package com.dokkaebi.domain.community;

import com.dokkaebi.domain.community.Comment;
import com.dokkaebi.service.TimeService;
import java.util.Date;
import lombok.Data;

@Data
public class CommentDTO {
  private Long id;
  private String content;
  private String createdAt;
  private String writerNickname;
  private Long writerId;

  public CommentDTO(Comment comment) {
    id = comment.getId();
    content = comment.getContent();
    createdAt = TimeService.toDate(comment.getCreatedAt());
    writerNickname = comment.getWriter().getNickname();
    writerId = comment.getWriter().getId();
  }

}
