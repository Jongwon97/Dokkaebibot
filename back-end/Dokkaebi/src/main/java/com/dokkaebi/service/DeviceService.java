package com.dokkaebi.service;

import com.dokkaebi.domain.Device;
import com.dokkaebi.mqtt.MqttService;
import com.dokkaebi.repository.DeviceRepository;
import com.dokkaebi.repository.MemberRepository;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeviceService {
  private final DeviceRepository deviceRepository;
  private final MemberRepository memberRepository;
  private final MqttService mqttService;
  public boolean registerDevice(Long memberId, String serialNumber) {

    // do nothing if serialNumber regex pattern doesn't match
    if (!Pattern.compile("^SN[0-9]{6}$").matcher(serialNumber).matches()) {
      return false;
    }

    if (!deviceRepository.isNew(serialNumber)) {
      return false;
    }

    Device device = new Device();
    device.setSerialNumber(serialNumber);
    device.setMember(memberRepository.findById(memberId).get());
    deviceRepository.save(device);
    mqttService.subscribe(serialNumber + "/#");
    return true;
  }

}
