import { client, clientWithToken } from './client';

const studyroomURL = 'studyrooms/';

export const getStudyRoomByTitle=(title:any)=>{
  return client().get(studyroomURL + `community/${title}`);
};

export const getStudyRoomsLatest = () => {
  return client().get(studyroomURL + 'community');
};
export const getTotalStudyRooms = () => {
  return client().get(studyroomURL + 'total');
};
export const getMyStudyRooms = () => {
  return clientWithToken().get(studyroomURL + 'myStudyRooms/check');
};

export const getRecommendStudyRooms = () => {
  return clientWithToken().get(studyroomURL + 'recommend/check');
};

export const getStudyRoomDetail = ({roomId}:{roomId:number}) => {
  return client().get(studyroomURL + `studyRoomDetail/${roomId}`);
};

export const getStudyRoomMembers=({roomId}:{roomId:number})=>{
  return client().get(studyroomURL + `members/${roomId}`);
};

export const getStudyRoomMember = ({ roomId, memberId }: { roomId: number, memberId: number }) => {
  return client().get(studyroomURL + `member/${roomId}/${memberId}`);
};

export const createStudyRoom = ({
  title,
  maxCapacity,
  lockStatus,
  password,
  hashTags,
  headerImg,
}: {
  title?: string;
  maxCapacity?: number;
  lockStatus?: number;
  password?: string;
  hashTags?: { tagName?: string[] }[];
  headerImg?: string;
}) => {
  return clientWithToken().post(
    studyroomURL + 'create/check',
    {
      title,
      maxCapacity,
      hashTags,
      lockStatus,
      password,
      headerImg,
    }
  );
};

// 스터디룸 디폴트 이미지 등록
export const updateStudyRoomDefaultImage = (roomId:number) => {
  return client().put(studyroomURL + `defaultImage/${roomId}`);
};


// 새로운 멤버 추가
export const updateStudyroomMember=({roomId}:{roomId:number})=>{
  return clientWithToken().post(studyroomURL + `enter/${roomId}/check`);
};

// 멤버 탈퇴
export const deleteMemberStudyroom=({roomId}:{roomId:number})=>{
  return clientWithToken().delete(studyroomURL + `exit/${roomId}/check`);
};

// 스터디룸 삭제
export const deleteStudyroom=({roomId}:{roomId:number})=>{
  return clientWithToken().delete(studyroomURL + `delete/${roomId}/check`);
};

// 채팅방 메세지 조회
export const getChats = ({roomId}:{roomId:number}) => {
  return client().get(studyroomURL + `getChats/${roomId}`);
};

// 스터디룸 디폴트 이미지 등록
export const updateStudyRoomMemberTime = ({ roomId, time }: { roomId: number, time: number }) => {
  return clientWithToken().put(studyroomURL + `member/updateTime/${roomId}/${time}/check`);
};
