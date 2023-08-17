package com.dokkaebi.domain.admin;

import com.dokkaebi.service.TimeService;
import lombok.Data;

@Data
public class NoticeDTO {
  private Long id;
  private String title;
  private String content;
  private String createdAt;

  public NoticeDTO(Notice notice) {
    id = notice.getId();
    title = notice.getTitle();
    content = notice.getContent();
    createdAt = TimeService.toDate(notice.getCreatedAt());
  }

}
