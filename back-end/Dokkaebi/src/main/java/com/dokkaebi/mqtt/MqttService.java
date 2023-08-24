package com.dokkaebi.mqtt;

import com.dokkaebi.controller.SocketController;
import com.dokkaebi.domain.ControlDeviceDTO;
import com.dokkaebi.domain.Device;
import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberAnalysis;
import com.dokkaebi.domain.StudyDataInputDTO;
import com.dokkaebi.domain.StudyStatusDTO;
import com.dokkaebi.domain.studyroom.SocketStudyRoomMemberDto;
import com.dokkaebi.repository.DeviceRepository;
import com.dokkaebi.repository.MemberAnalysisRepository;
import com.dokkaebi.repository.MemberRepository;
import com.dokkaebi.repository.StudyRoom.StudyRoomMemberRepository;
import com.dokkaebi.service.MemberAnalysisService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MqttService {
  private final MessageChannel mqttOutboundChannel;
  public final MqttPahoMessageDrivenChannelAdapter mqttPahoMessageDrivenChannelAdapter;
  public final MessageHandler mqttInboundMessageHandler;
  public final MqttRepository mqttRepository;
  public final StudyRoomMemberRepository studyRoomMemberRepository;
  public final SocketController socketController;
  public final DeviceRepository deviceRepository;
  public final MemberAnalysisService memberAnalysisService;


  public void publish(String topic, Object payload) {
    mqttOutboundChannel.send(
        MessageBuilder.withPayload(payload)
            .setHeader("mqtt_topic", topic)
            .build());
  }

  public void subscribe(String topic) {
    try {
      mqttPahoMessageDrivenChannelAdapter.addTopic(topic);
    } catch (MessagingException e) {
      // if topic already exists
      System.out.println("ERROR AT: add new topic, MqttService/subscribe");
      System.out.println(e);
    }
  }
  public void unSubscribe(String topic) {
    try {
      mqttPahoMessageDrivenChannelAdapter.removeTopic(topic);
    } catch (MessagingException e) {
      // if topic doesn't exist
      System.out.println("ERROR AT: remove topic, MqttService/unSubscribe");
      System.out.println(e);
    }
  }

  private String[] parseMessage(Message<?> message) {
    String topic = message.getHeaders()
        .get("mqtt_receivedTopic", String.class);
    if (topic == null) return new String[0];
    String[] parsed = topic.split("/");
    if (parsed.length == 0) return new String[0];
    return parsed;
  }
  
  public void handleMessage(Message<?> message) {
    String[] parsedTopic = parseMessage(message);
    if (parsedTopic.length <= 1) return;
    Optional<Device> optionalDevice = deviceRepository.findBySerialNumber(parsedTopic[0]);
    if (optionalDevice.isEmpty()) {
      return;
    }
    Member member = optionalDevice.get().getMember();
    // get memberId's studyRoom id
    Long studyroomId = studyRoomMemberRepository.findRoomIdByMemberId(member.getId());

    // parse message data to send
    String payload = (String) message.getPayload();
    switch (parsedTopic[1]) {
      case "camera" -> {
        // send data to studyRoom id's Socket URL
        try {
          SocketStudyRoomMemberDto dto = new SocketStudyRoomMemberDto(
              member.getId(), studyroomId, member.getIconNumber(), payload);
          socketController.sendStatus(studyroomId, dto);
        } catch (IndexOutOfBoundsException e) {
          System.out.println(e);
        }
      }
      case "atmosphere" -> {
//        System.out.println("CASE \"atmosphere\"");
//        // send data to studyRoom id's Socket URL
//        try {
//          SocketStudyRoomMemberDto dto = new SocketStudyRoomMemberDto(
//              member.getId(), studyroomId, payload);
//          socketController.sendStatus(studyroomId, dto);
//        } catch (IndexOutOfBoundsException e) {
//          System.out.println(e);
//        }
      }
      case "data" -> {
//        System.out.println("CASE \"data\"");
//        Gson gson = new Gson();
//        Map<String, Object> data = gson.fromJson((String) message.getPayload(), Map.class);
//        // compare topic's SN and data's SN
//        System.out.println("SN: " + data.get("serialNumber") + " / " + parsedTopic[0]);
//        if (!parsedTopic[0].equals(data.get("serialNumber"))) return;
//        mqttRepository.saveStudyData(member, data);
//        memberAnalysisService.analyzeAndSave(member, payload);
      }
      case "power" -> {
        optionalDevice.get().setStatus(payload);
        try {
          SocketStudyRoomMemberDto dto = new SocketStudyRoomMemberDto(
              member.getId(), member.getIconNumber(), studyroomId);
          socketController.sendStatus(studyroomId, dto);
        } catch (IndexOutOfBoundsException e) {
          System.out.println(e);
        }
      }
    }
  }

  public void saveData(StudyDataInputDTO data) {

    Optional<Device> optionalDevice = deviceRepository.findBySerialNumber(data.getSerialNumber());
    if (optionalDevice.isEmpty()) { return; }

    Member member = optionalDevice.get().getMember();
    mqttRepository.saveStudyData(member, data);
    memberAnalysisService.analyzeAndSave(member, data);
  }

//  public void register(Long memberId, String serialNumber) {
//    Optional<Member> optionalMember = memberRepository.findById(memberId);
//    Member member = optionalMember.get();
//
//    member.setSerialNumber(serialNumber);
//  }

//  public void startDeviceConnection(Long memberId) {
//    subscribe(getSerialNumberFromMemberId(memberId) + "/#");
//
//  }
//
//  public void finishDeviceConnection(Long memberId) {
//    unSubscribe(getSerialNumberFromMemberId(memberId) + "/#");
//  }
//
//  public void control(Long memberId, ControlDeviceDTO controlDeviceDTO) {
//    publish(getSerialNumberFromMemberId(memberId) + "/control",
//        controlDeviceDTO);
//  }
//
//  private String getSerialNumberFromMemberId(Long memberId) {
//    return memberRepository.findById(memberId).get().getSerialNumber();
//  }
}
