import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface StudyRoomMember {
  id: number;
  nickname: string;
  iconNumber: number;
  isActive: number;
  host: number;
  time: number;
  defaultIcon: number;
}

const initialState: StudyRoomMember[] = [];

const studymemberSlice = createSlice({
  name: 'studyroom',
  initialState: initialState,
  reducers: {
    setMemberList: (state, action: PayloadAction<StudyRoomMember[]>) => {
      return {
        ...action.payload,
      };
    },
  },
});

export const { setMemberList } = studymemberSlice.actions;

export default studymemberSlice.reducer;
