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
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "device")
@Getter
@Setter
public class Device {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "device_id")
  private Long id;

  @Column(unique = true)
  @NotNull
  private String serialNumber;

  private String status;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "member_id", referencedColumnName = "member_id")
  private Member member;

}
