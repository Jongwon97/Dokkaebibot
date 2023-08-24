import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface Studyroom {
  room_id?: number;
  id: number;
  title: string;
  createMemberId?: number;
  createMemberNickname?: string;
  curAttendance?: number;
  maxCapacity: number;
  memberList?: number[];
  tagNames?: string[];
  lockStatus: number;
  password?: string;
  headerImg?: any;
}

const initialState: Studyroom = {
  room_id: 0,
  id: 0,
  title: '',
  createMemberId: 0,
  createMemberNickname: '',
  curAttendance: 1,
  maxCapacity: 4,
  memberList: [],
  tagNames: [],
  lockStatus: 0,
  password: '',
  headerImg: undefined,
};

const studyroomSlice = createSlice({
  name: 'studyroom',
  initialState: initialState,
  reducers: {
    setRoomData: (state, action: PayloadAction<Studyroom>) => {
      return {
        ...action.payload,
      };
    },
  },
});

export const { setRoomData } = studyroomSlice.actions;
export default studyroomSlice.reducer;
