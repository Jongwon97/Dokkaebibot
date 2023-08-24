package com.dokkaebi.domain.community;

import com.dokkaebi.domain.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter @Setter
@NoArgsConstructor
public class Comment extends CommunityEntity{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long id;

  private String content;
  private Date createdAt;

  @PrePersist
  private void onCreate() {
    if (createdAt == null) {
      createdAt = new Date();
    }
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "article_id") // set FK
  private Article article;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id") // set FK
  private Member writer;


}
