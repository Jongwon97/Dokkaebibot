package com.dokkaebi.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "member_analysis")
@Getter @Setter
@NoArgsConstructor
public class MemberAnalysis {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_analysis_id")
  private Long id;


  private Long timeTotalStudy;
  private Long timeNetStudy;
  private Long timeGoodPose;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "member_id", referencedColumnName = "member_id")
  private Member member;

  public MemberAnalysis(Member m) {
    member = m;
    timeTotalStudy = 0L;
    timeNetStudy = 0L;
    timeGoodPose = 0L;
  }
}
