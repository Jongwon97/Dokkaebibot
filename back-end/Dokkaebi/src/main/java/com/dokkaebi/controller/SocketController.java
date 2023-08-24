package com.dokkaebi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberResponseDTO;
import com.dokkaebi.domain.studyroom.SocketStudyRoomMemberDto;
import com.dokkaebi.domain.studyroom.chat.ChatDto;
import com.dokkaebi.mqtt.MqttService;
import com.dokkaebi.repository.DeviceRepository;
import com.dokkaebi.service.MemberService;
import com.dokkaebi.service.studyroom.ChatService;
import com.dokkaebi.service.studyroom.StudyRoomService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class SocketController {
 
    private final SimpMessagingTemplate messagingTemplate;
    
    private ChatService chatService;
    
    private StudyRoomService studyRoomService;

    private MqttService mqttService;
    
    private DeviceRepository deviceRepository;
    
    private MemberService memberService;
    
    @Autowired
    public SocketController(SimpMessagingTemplate messagingTemplate,
        ChatService chatService,StudyRoomService studyRoomService,
        @Lazy MqttService mqttService, DeviceRepository deviceRepository,
        @Lazy MemberService memberService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService=chatService;
        this.studyRoomService=studyRoomService;
        this.mqttService = mqttService;
        this.deviceRepository = deviceRepository;
        this.memberService=memberService;
    }
	
    // 메시지 전송 -> /sub/chat/roomId 를 구독한 subscriber에게 모두 전송
    // 'pub/sendMessage'
    @MessageMapping("/sendMessage")
    public void broadcasting(ChatDto chatDto) {
    	chatService.registMessage(chatDto);
    	messagingTemplate.convertAndSend("/sub/chat/"+chatDto.getRoomId(),chatDto);
    }
    // 이전 채팅 기록 조회
    @ResponseBody
    @GetMapping("/api/studyrooms/getChats/{roomId}")
    public List<ChatDto> getStudyRoomChats(@PathVariable Long roomId){
    	return chatService.getStudyRoomChats(roomId);
    }
    
    // 스터디룸 멤버가 스터디룸에 접속하거나 나갈때 처리
    @MessageMapping("/isActive")
    public void setMemberActive(SocketStudyRoomMemberDto memberState) {
    	Optional<String> device=deviceRepository.findSerialNumberByMemberId(memberState.getMemberId());
    	if (memberState.getIsActive() == 1) {
    		// StartDate 저장
    		studyRoomService.setStudyRoomMemberStartTime(memberState.getMemberId(), memberState.getRoomId());
    		// mqtt 구독 처리
        	if(device.isPresent()) {
        		mqttService.subscribe(device.get()+ "/#");
        	}
    	} 
    	else {
    		if(device.isPresent()) {
    		mqttService.unSubscribe(device.get()+ "/#");
    		}
    	}
    	studyRoomService.setStudyRoomMemberIsActive(memberState.getMemberId(), memberState.getRoomId(), memberState.getIsActive());
    	messagingTemplate.convertAndSend("/sub/studyRoom/"+memberState.getRoomId(),memberState);
    }
  
  // 스터디룸 나가기
   @ResponseBody
   @DeleteMapping("/api/studyrooms/exit/{roomId}/check")
   public void exitStudyRoom(@PathVariable Long roomId, HttpServletRequest request) {
		Member member=new Member();
		member.setId((Long)request.getAttribute("id"));
		// DB 삭제 처리
		studyRoomService.exitStudyRoom(member.getId(), roomId);
		// 소켓으로 스터디룸 멤버들에게 전달
		SocketStudyRoomMemberDto memberState=new SocketStudyRoomMemberDto();
		memberState.setType("exit");
		memberState.setMemberId(member.getId());
		messagingTemplate.convertAndSend("/sub/studyRoom/"+roomId,memberState);
   }
    
   // 스터디룸 삭제
   @ResponseBody
   @DeleteMapping("/api/studyrooms/delete/{roomId}/check")
   public void deleteStudyRoom(@PathVariable Long roomId, HttpServletRequest request) {
		// DB 삭제 처리
		studyRoomService.deleteStudyRoom(roomId);
		// 소켓으로 멤버들에게 전달
		SocketStudyRoomMemberDto memberState=new SocketStudyRoomMemberDto();
		memberState.setType("destroy");
		messagingTemplate.convertAndSend("/sub/studyRoom/"+roomId,memberState);
   }
   
   // Iot에서 유저 상태 정보 전송
   public void sendStatus(Long roomId, SocketStudyRoomMemberDto status) {
	      messagingTemplate.convertAndSend("/sub/studyRoom/" + roomId, status);
   }
   
   // 스터디룸 멤버 시간 변경
   @ResponseBody
   @PutMapping("/api/studyrooms/member/updateTime/{roomId}/{time}/check")
   public void updateStudyRoomMemberTime(@PathVariable Long roomId,@PathVariable int time ,HttpServletRequest request) {
	   // Member에서 DB조회
	   MemberResponseDTO member=memberService.userInfo((Long)request.getAttribute("id"));
	   studyRoomService.setStudyRoomMemberTime(member.getId(), roomId, time);
	   SocketStudyRoomMemberDto dto=new SocketStudyRoomMemberDto(member.getId(),roomId,member.getIconNumber(),time);
	   messagingTemplate.convertAndSend("/sub/studyRoom/"+roomId, dto);
   }
}
