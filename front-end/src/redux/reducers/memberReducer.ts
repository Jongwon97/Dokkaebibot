import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { PURGE } from 'redux-persist';

export interface Member {
  id: number;
  email: string;
  nickname: string;
  password: string;
  passwordConfirm: string;
  iconNumber: number;
  info?: string;
  interests?: string[];
  haveDevice: boolean;
  isAdmin: boolean;
}

const initialState: Member = {
  id: 0,
  email: '',
  nickname: '',
  password: '',
  passwordConfirm: '',
  iconNumber: 0,
  info: '',
  interests: [],
  haveDevice: false,
  isAdmin: false
};

const memberSlice = createSlice({
  name: 'member',
  initialState: initialState,
  reducers: {
    setMemberData: (state, action: PayloadAction<Member>) => {
      return {
        ...action.payload,
      };
    },
    logoutAction: (state, action: PayloadAction<void>) => {
      return {
        ...initialState,
      };
    },
    setHaveDevice: (state, action: PayloadAction<boolean>) => {
      state.haveDevice = action.payload;
    },
    setMemberInfo: (state, action: PayloadAction<string>) => {
      state.info = action.payload;
    },
    setMemberInterests: (state, action: PayloadAction<string[]>) => {
      state.interests = action.payload;
    },
    setMemberNickname: (state, action: PayloadAction<string>) => {
      state.nickname = action.payload;
    },
  },
  extraReducers: (builder) => {
      builder.addCase(PURGE, () => {return initialState})
  },
});

export const { setMemberData, logoutAction, setHaveDevice, 
  setMemberInfo, setMemberInterests, setMemberNickname } = memberSlice.actions;
export default memberSlice.reducer;
