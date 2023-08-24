package com.dokkaebi.controller;

import com.dokkaebi.domain.studyroom.SocketStudyRoomMemberDto;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.studyroom.StudyRoom;
import com.dokkaebi.domain.studyroom.StudyRoomDto;
import com.dokkaebi.domain.studyroom.StudyRoomMember;
import com.dokkaebi.domain.studyroom.StudyRoomMemberDto;
import com.dokkaebi.service.studyroom.StudyRoomService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/studyrooms")
public class StudyRoomController {

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private StudyRoomService service;
	
	// Community 페이지에서 볼 수 있는 StudyRoom 목록 10개 조회(가장 최근에 생성된 순서로 10개)
	@GetMapping("/community")
	public ResponseEntity<List<StudyRoomDto>> findStudyRoomNewest() throws Exception{
			return new ResponseEntity<List<StudyRoomDto>>(service.getRecent10StudyRooms(), HttpStatus.OK);
	}
	// 전체 스터디룸 조회
	@GetMapping("/total")
	public ResponseEntity<List<StudyRoomDto>> findTotalStudyRooms() throws Exception{
			return new ResponseEntity<List<StudyRoomDto>>(service.getTotalStudyRooms(), HttpStatus.OK);
	}
	
	// title을 받아서 포함되는 스터디방 조회
	@GetMapping("/community/{title}")
	public ResponseEntity<List<StudyRoomDto>> findStudyRoomByTitle(@PathVariable String title) throws Exception{
		return new ResponseEntity<List<StudyRoomDto>>(service.getStudyRoomsByTitle(title), HttpStatus.OK);
	}
	
	// 자신이 들어가 있는 StudyRoom들 조회
	@GetMapping("/myStudyRooms/check")
	public ResponseEntity<List<StudyRoomDto>> findMyStudyRoom(HttpServletRequest request) throws Exception{
		return new ResponseEntity<List<StudyRoomDto>>(service.getMyStudyRooms((Long)request.getAttribute("id")), HttpStatus.OK);
	}
	
	// 사용자별 스터디룸 추천 조회
	@GetMapping("/recommend/check")
	public ResponseEntity<List<StudyRoomDto>> findRecommendedStudyRooms(HttpServletRequest request) throws Exception{
		return new ResponseEntity<List<StudyRoomDto>>(service.getRecommendedStudyRooms((Long)request.getAttribute("id")), HttpStatus.OK);
	}
	
	// StudyRoomDetail 정보 반환
	@GetMapping("/studyRoomDetail/{roomId}")
	public ResponseEntity<StudyRoomDto> findStudyRoomDetail(@PathVariable Long roomId) throws Exception{
			return new ResponseEntity<StudyRoomDto>(service.getStudyRoomDto(roomId), HttpStatus.OK);
	}
	
	// 스터디방에 속한 멤버 반환
	@GetMapping("/members/{roomId}")
	public ResponseEntity<List<StudyRoomMemberDto>> findStudyRooomMembers(@PathVariable Long roomId) throws Exception{
		return new ResponseEntity<List<StudyRoomMemberDto>>(service.getStudyRoomMembers(roomId), HttpStatus.OK);
	}
	
	// 스터디방에 속한 멤버 한명 반환
	@GetMapping("/member/{roomId}/{memberId}")
	public ResponseEntity<StudyRoomMemberDto> findStudyRooomMember(@PathVariable Long roomId, @PathVariable Long memberId) throws Exception{
		return new ResponseEntity<StudyRoomMemberDto>(service.getStudyRoomMember(roomId,memberId), HttpStatus.OK);
	}
	
	// Study 방 생성
	// Client에서 Studyroom 생성 post 요청을 보내면 방 생성 후, roomId(기본키) 반환
	// Client에서는 room 생성 성공 시 roomId와 Image를 S3Contorller로 보내서 처리함.
	@PostMapping("/create/check")
	public ResponseEntity<?> createStudyRoom(@RequestBody StudyRoom studyRoom, HttpServletRequest request) throws Exception{
		Member member=new Member();
		member.setId((Long)request.getAttribute("id"));
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		
		Long roomId=service.createStudyRoom(studyRoom, member);
		if (roomId!=null) {
			resultMap.put("roomId", roomId);
			status = HttpStatus.ACCEPTED;
			resultMap.put("message", SUCCESS);
			return new ResponseEntity<Map<String, Object>>(resultMap,status);
		} else {
			resultMap.put("message", FAIL);
			status = HttpStatus.ACCEPTED;
			return new ResponseEntity<Map<String, Object>>(resultMap,status);
		}
	}
	
	// StudyRoom 입장 -> Study_Room_Member에 멤버 추가
	@PostMapping("/enter/{roomId}/check")
	public ResponseEntity<?> createStudyRoom(@PathVariable Long roomId, HttpServletRequest request) throws Exception{
		Member member=new Member();
		member.setId((Long)request.getAttribute("id"));
		
		StudyRoom studyRoom=new StudyRoom();
		studyRoom.setId(roomId);
		
		StudyRoomMember studyRoomMember=new StudyRoomMember();
		studyRoomMember.setMember(member);
		studyRoomMember.setStudyRoom(studyRoom);
		
		Map<String, Object> resultMap = new HashMap<>();
		String check=service.enterStudyRoom(studyRoomMember);		
		resultMap.put("message", check);
		
		return new ResponseEntity<Map<String, Object>>(resultMap,HttpStatus.OK);
		
	}
	
	// 스터디룸 생성시 이미지 등록을 안했을때 default 이미지 설정
    @PutMapping("/defaultImage/{roomId}")
    public String defaultStudyRoomImage(@PathVariable Long roomId) {
    	service.setStudyRoomImage("https://project-dokkaebi.s3.ap-northeast-2.amazonaws.com/images/default/1.jpg", roomId);
    	return "success";
    }
}
