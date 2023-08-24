package com.dokkaebi.domain.community;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.studyroom.StudyRoom;
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
import jakarta.persistence.UniqueConstraint;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "invite",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sender_id", "receiver_id"})
    })
@Getter @Setter
public class Invite {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "invite_id")
  private Long id;

  @Column(columnDefinition = "boolean default true")
  private boolean notRead;
  private Date createdAt;

  @PrePersist
  private void onCreate() {
    if (createdAt == null) {
      createdAt = new Date();
    }
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "sender_id")
  private Member sender;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "studyRoom_id")
  private StudyRoom studyRoom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id")
  private Member receiver;


}
