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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "scrap",
    uniqueConstraints={@UniqueConstraint(columnNames = {"member_id", "article_id"})}
)
@Getter
@Setter
@NoArgsConstructor
public class Scrap {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "scrap_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "article_id")
  private Article article;

  public Scrap(Article _article, Member _member) {
    article = _article;
    member = _member;
  }
}
