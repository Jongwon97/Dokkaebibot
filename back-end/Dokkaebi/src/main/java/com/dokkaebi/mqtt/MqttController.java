package com.dokkaebi.mqtt;

import com.dokkaebi.domain.ControlDeviceDTO;
import com.dokkaebi.domain.StudyDataInputDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mqtt")
public class MqttController {

  private final MqttService mqttService;

  @PostMapping("/data")
  public ResponseEntity<?> postMqttData(
      @RequestBody StudyDataInputDTO data) {

    mqttService.saveData(data);
    return new ResponseEntity<>(HttpStatus.OK);
  }

//  @PostMapping("control")
//  public ResponseEntity<?> controlDevice(
//      HttpServletRequest httpServletRequest,
//      @RequestBody ControlDeviceDTO controlDeviceDTO) {
//
//    Long memberId = (Long) httpServletRequest.getAttribute("id");
//    mqttService.control(memberId, controlDeviceDTO);
//    return new ResponseEntity<>(HttpStatus.OK);
//  }
//
//  @GetMapping("add")
//  public ResponseEntity<?> addTopic(
//      @RequestParam("topic") String newTopic) {
//    System.out.println("3");
//    System.out.println("4");
//    System.out.println(newTopic);
//    mqttService.subscribe(newTopic);
//    return new ResponseEntity<>(HttpStatus.OK);
//  }
//  @GetMapping("remove")
//  public ResponseEntity<?> removeTopic(
//      @RequestParam("topic") String topic) {
//    System.out.println("3");
//    System.out.println("4");
//    System.out.println(topic);
//    mqttService.unSubscribe(topic);
//    return new ResponseEntity<>(HttpStatus.OK);
//  }

//  @PostMapping("device")
//  public ResponseEntity<?> registerDevice(
//      HttpServletRequest httpServletRequest,
//      @RequestBody String serialNumber) {
//    Long memberId = (Long) httpServletRequest.getAttribute("id");
//    mqttService.register(memberId, serialNumber);
//    return new ResponseEntity<>(HttpStatus.OK);
//  }
}
