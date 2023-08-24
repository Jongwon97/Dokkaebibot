package com.dokkaebi.domain.community;

import com.dokkaebi.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article")
@Getter @Setter
@NoArgsConstructor
public class Article extends CommunityEntity{
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "article_id")
  private Long id;

  @Column(length = 100)
  private String title;

  @Column(length = 3000)
  private String content;

  private Date createdAt;

  @PrePersist
  private void onCreate() {
    if (createdAt == null) {
      createdAt = new Date();
    }
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id") // set FK
  private Member writer;

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
  private List<Comment> commentList = new ArrayList<>();

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
  private List<Like> likeList = new ArrayList<>();

  @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
  private List<Scrap> scrapList = new ArrayList<>();
}
