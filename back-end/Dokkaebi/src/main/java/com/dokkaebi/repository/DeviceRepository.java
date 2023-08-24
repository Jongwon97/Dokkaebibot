package com.dokkaebi.repository;

import com.dokkaebi.domain.Device;
import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.community.Invite;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeviceRepository {

  private final EntityManager entityManager;

  public void save(Device device) {
    entityManager.persist(device);
  }

  public Optional<Device> findBySerialNumber(String serialNumber) {
    String query = "SELECT d FROM Device d "
        + "WHERE d.serialNumber = :serialNumber";
    return entityManager.createQuery(query, Device.class)
        .setParameter("serialNumber", serialNumber)
        .getResultList()
        .stream().findFirst();
  }
  public Optional<String> findSerialNumberByMemberId(Long memberId) {
    String query = "SELECT d.serialNumber FROM Device d "
        + "WHERE d.member.id = :memberId";
    return entityManager.createQuery(query, String.class)
        .setParameter("memberId", memberId)
        .getResultList()
        .stream().findFirst();
  }

  public boolean isNew(String serialNumber) {
    String query = "SELECT d.serialNumber FROM Device d "
        + "WHERE d.serialNumber = :serialNumber";
    return entityManager.createQuery(query, String.class)
        .setParameter("serialNumber", serialNumber)
        .getResultList()
        .isEmpty();
  }
}
