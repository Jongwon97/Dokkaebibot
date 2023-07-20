package com.dokkaebi.service.studyroom;

import com.dokkaebi.domain.Member;
import com.dokkaebi.domain.studyroom.StudyRoom;

public interface StudyRoomService {
	public boolean createStudyRoom(StudyRoom studyRoom, Member member);
}
