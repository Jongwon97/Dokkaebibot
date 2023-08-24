package com.dokkaebi.service.studyroom;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.MemberInterest;
import com.dokkaebi.domain.studyroom.HashTag;
import com.dokkaebi.domain.studyroom.StudyRoom;
import com.dokkaebi.domain.studyroom.StudyRoomDto;
import com.dokkaebi.domain.studyroom.StudyRoomMember;
import com.dokkaebi.domain.studyroom.StudyRoomMemberDto;
import com.dokkaebi.repository.MemberInfoRepository;
import com.dokkaebi.repository.StudyRoom.HashTagRepository;
import com.dokkaebi.repository.StudyRoom.StudyRoomMemberRepository;
import com.dokkaebi.repository.StudyRoom.StudyRoomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Transactional
@Service
public class StudyRoomServiceImpl implements StudyRoomService{

	
	private static final String String = null;

	@Autowired
	private StudyRoomRepository SR;

	@Autowired
	private StudyRoomMemberRepository SMR;
	
	@Autowired
	private HashTagRepository HR;
	
	@Autowired
	private MemberInfoRepository MIR;

	@PersistenceContext
	private EntityManager em;
	
	// 스터디룸 생성
	@Override
	public Long createStudyRoom(StudyRoom studyRoom, Member member) {
		studyRoom.setCreatedDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		
		// StudyRoomMember 셋팅
		StudyRoomMember studyRoomMember=new StudyRoomMember(); 
		studyRoomMember.setStudyRoom(studyRoom);
		studyRoomMember.setMember(member);
		studyRoomMember.setHost(1); // 방 생성시 방장으로 지정
		studyRoom.getMembers().add(studyRoomMember);
						
		// HashTag StudyRoom과 외래키 설정
		for(int i=0;i<studyRoom.getHashTags().size();i++) {
			studyRoom.getHashTags().get(i).setStudyRoom(studyRoom);
		}
		
		studyRoom.setCurAttendance(1);
		StudyRoom newStudyRoom=SR.save(studyRoom); // 스터디룸 생성		
		return newStudyRoom.getId();
	}

	// 제목으로 스터디룸 검색
	@Override
	public List<StudyRoomDto> getStudyRoomsByTitle(String title) {
		List<StudyRoom> studyRooms=SR.findByTitleContaining(title);
		List<StudyRoomDto> responseStudyRooms=new ArrayList<>();
		for(StudyRoom studyRoom:studyRooms) {
			responseStudyRooms.add(new StudyRoomDto(studyRoom));
		}
		return responseStudyRooms;
	}
	
	// 전체 스터디룸 조회
	public List<StudyRoomDto> getTotalStudyRooms(){
		Sort descendingSort = Sort.by(Sort.Direction.DESC, "id");
		List<StudyRoom> studyRooms=SR.findAll(descendingSort);
		List<StudyRoomDto> responseStudyRooms=new ArrayList<>();
		for(StudyRoom studyRoom:studyRooms) {
			responseStudyRooms.add(new StudyRoomDto(studyRoom));
		}
		return responseStudyRooms;
	}
	
	// 최근 생성된 스터디룸 10개 조회
	@Override
	public List<StudyRoomDto> getRecent10StudyRooms() {
		List<StudyRoom> studyRooms=SR.findTop10ByOrderByCreatedDateDesc();
		List<StudyRoomDto> responseStudyRooms=new ArrayList<>();
		for(StudyRoom studyRoom:studyRooms) {
			responseStudyRooms.add(new StudyRoomDto(studyRoom));
		}
		return responseStudyRooms;
	}
	
	// 사용자별 스터디룸 조회
	@Override
	public List<StudyRoomDto> getMyStudyRooms(Long memberId) {
		List<StudyRoomMember> studyRoomMembers=SMR.findAllByMemberId(memberId);
		List<StudyRoom> myStudyRooms=new ArrayList<>();
		
		// studyRoomMember를 통해 myStudyRooms 조회 
		for(StudyRoomMember studyRoomMember : studyRoomMembers) {
			StudyRoom studyroom=studyRoomMember.getStudyRoom();
			myStudyRooms.add(studyroom);
		}

		// 반환용 StudyRoomDto 생성
		List<StudyRoomDto> responseStudyRooms=new ArrayList<>();
		for(StudyRoom studyRoom : myStudyRooms) {
			responseStudyRooms.add(new StudyRoomDto(studyRoom));
		}
	
		return responseStudyRooms;
	}
	
	// 유저별 추천 스터디룸 조회
	@Override
	public List<StudyRoomDto> getRecommendedStudyRooms(Long memberId) {
		// memberInterest에서 tagNames 리스트 불러오기
		List<MemberInterest> memberInterests=MIR.findByMemberId(memberId);
		List<Long> roomList=new ArrayList<>();
		
		List<HashTag> tagList=new ArrayList<>();
		for(MemberInterest interest: memberInterests) {
			// hashtag 검색해서 roomId 리스트 가져옴
			List<HashTag> tempList=HR.findByTagNameContaining(interest.getTagName());
			if(tagList.size()>5) {
				tagList=tempList;
				break;
			}
			else {
				for(HashTag tag:tempList) {
					tagList.add(tag);
					if(tagList.size()>5) {
						break;
					}
				}
			}
		}
		List<StudyRoomDto> recommendedStudyRooms=new ArrayList<>();
		for(int i=0;i<tagList.size();i++) {
			HashTag tag=tagList.get(i);
			recommendedStudyRooms.add(new StudyRoomDto(tag.getStudyRoom()));
		}
		if(recommendedStudyRooms.size()==0) {
			recommendedStudyRooms=getRecent10StudyRooms();
		}
		return recommendedStudyRooms;
	}
	
	// roomId를 받아 StudyRoomDto로 반환 Detail 페이지
	@Override
	public StudyRoomDto getStudyRoomDto(Long roomId) {
		Optional<StudyRoom> studyroom=SR.findById(roomId);
	    if (studyroom.isPresent()) {	    	
	        return new StudyRoomDto(studyroom.get());
	    } else {
	        // 해당 roomId에 해당하는 StudyRoom이 존재하지 않는 경우 처리할 로직 추가
	        return null;
	    }
	}
	
	// 스터디방에 속한 멤버 반환
	@Override
	public List<StudyRoomMemberDto> getStudyRoomMembers(Long roomId) {
		List<StudyRoomMember> list=SMR.findAllByStudyRoomId(roomId);
		List<StudyRoomMemberDto> members = new ArrayList<>();
		
		for(StudyRoomMember studyRoomMember : list) {
			Member member=studyRoomMember.getMember();
			StudyRoomMemberDto dto=new StudyRoomMemberDto(member,studyRoomMember.getHost(),studyRoomMember.getIsActive(),studyRoomMember.getTime());					
			// 유저가 활동 중일 경우
			if(studyRoomMember.getIsActive()==1) {
				// StartTime 초로 변환
		        int startHours= studyRoomMember.getStartTime().getHour();
		        int startMinutes=studyRoomMember.getStartTime().getMinute();
		        int startSeconds=studyRoomMember.getStartTime().getSecond();
		        int startTotalSeconds = startHours * 3600 + startMinutes * 60 + startSeconds;
				
		        // 현재 시간 초로 변환
				LocalTime currentTime = LocalTime.now(); // 현재 시간
		        int curHours = currentTime.getHour();
		        int curMinutes = currentTime.getMinute();
		        int curSeconds = currentTime.getSecond();
		        int curTotalSeconds = curHours * 3600 + curMinutes * 60 + curSeconds;
		        
		        
		        int seconds=0;
		        // 시작 시간이 더 큰 경우 -> 밤부터 새벽까지 공부중
		        if(startTotalSeconds>curTotalSeconds) {
		        	seconds= 86400 -startTotalSeconds+curTotalSeconds;
		        }
		        else { // 시작 시간이 작은 경우
		        	seconds=curTotalSeconds-startTotalSeconds;
		        }
		        System.out.println(seconds);
				dto.setTime(seconds+dto.getTime());
			}
			members.add(dto);
		}
		return members;
	}
	
	// 새로 들어온 멤버 한명 반환
	public StudyRoomMemberDto getStudyRoomMember(Long roomId,Long memberId) {
		StudyRoomMember member=SMR.findByMemberIdAndStudyRoomId(memberId, roomId);
		StudyRoomMemberDto dto=new StudyRoomMemberDto(member.getMember(),member.getHost(),member.getIsActive(),member.getTime());
		return dto;
	}
	
	// 이미지 S3 주소 저장
	@Override
	public boolean setStudyRoomImage(String imageUrl, Long roomId) {
		SR.setStudyRoomImage(imageUrl, roomId);
		return true;
	}

	// 스터디룸 입장 -> 유저 정보 DB에 반영(StudyRoomMember에 유저 추가)
	@Override
	public String enterStudyRoom(StudyRoomMember studyRoomMember) {
		// 해당 스터디룸에 이미 가입해 있는지 확인
		StudyRoomMember member=SMR.findByMemberIdAndStudyRoomId(studyRoomMember.getMember().getId(), studyRoomMember.getStudyRoom().getId());			
		// 가입해 있지 않은 멤버일 경우
		if(member==null) {
			Optional<StudyRoom> studyroom=SR.findById(studyRoomMember.getStudyRoom().getId()); // roomId로 스터디룸 가져옴.
			// 스터디룸에 가입한 인원이 max 일 경우
			if(studyroom.get().getCurAttendance()==studyroom.get().getMaxCapacity()) {
				return "overcapacity";
			}
			else {
				studyroom.get().setCurAttendance(studyroom.get().getCurAttendance()+1); // 스터디룸 인원 추가
				SMR.save(studyRoomMember);
				return "success";
			}
		}
		// 이미 가입한 멤버일 경우
		else {
			return "hi";
		}
	}
	
	// 스터디룸 나가기
	@Override
	public boolean exitStudyRoom(Long memberId, Long roomId) {
		StudyRoomMember member=SMR.findByMemberIdAndStudyRoomId(memberId, roomId); // 불러와서
		SMR.delete(member); // 삭제
		//스터디룸 현재 인원 수정
		Optional<StudyRoom> studyroom=SR.findById(roomId);
		if (studyroom.isPresent()) {
		    StudyRoom room = studyroom.get();
		    room.setCurAttendance(room.getCurAttendance()-1);
		    SR.save(room);
		}
		return true;
	}
	
	// 스터디룸 삭제
	@Override
	public boolean deleteStudyRoom(Long roomId) {
		Optional<StudyRoom> studyroom=SR.findById(roomId);
	    StudyRoom room=studyroom.get();
	    SR.delete(room);
		return true;
	}

	// 스터디룸 활동(입장,퇴장) 여부
	@Override
	public boolean setStudyRoomMemberIsActive(Long memberId, Long roomId, int isActive) {
		SMR.updateIsActiveByMemberIdAndRoomId(memberId, roomId, isActive);
		return true;
	}

	// 스터디룸 멤버 시간 변경
	@Override
	public boolean setStudyRoomMemberTime(Long memberId, Long roomId, int time) {
		SMR.updateTimeByMemberIdAndRoomId(memberId, roomId, time);
		return true;
	}
	
	// 스터디룸 멤버 startDate 변경
	@Override
	public boolean setStudyRoomMemberStartTime(Long memberId, Long roomId) {
		LocalTime currentTime = LocalTime.now();
		SMR.updateStartTimeByMemberIdAndRoomId(memberId, roomId, currentTime);
		return true;
	}


}
