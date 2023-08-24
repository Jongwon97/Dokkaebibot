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
import lombok.Setter;

@Entity
@Table(name = "friend",
    uniqueConstraints = {
      @UniqueConstraint(columnNames = {"member1_id", "member2_id"})
    })
@Getter @Setter
public class Friend {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "friend_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member1_id")
  private Member member1;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member2_id")
  private Member member2;

}
