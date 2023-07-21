package com.dokkaebi.service.studyroom;

import java.util.List;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.studyroom.StudyRoom;

public interface StudyRoomService {
	public boolean createStudyRoom(StudyRoom studyRoom, Member member); // 스터디룸 생성
	public List<StudyRoom> getRecent10StudyRooms(); 					// 가장 최근에 생성된 스터디룸 10개 반환 
}
