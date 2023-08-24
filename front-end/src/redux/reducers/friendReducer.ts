import { createSlice, PayloadAction } from '@reduxjs/toolkit';

export interface Friend {
  id: number;
  friendId: number;
  friendEmail: string;
  friendNickname: string;
  friendIconNumber: number;
  isNewMessage: boolean;
}

const initialState: Friend = {
  id: 0,
  friendId: 0,
  friendEmail: '',
  friendNickname: '',
  friendIconNumber: 0,
  isNewMessage: false,
};

const friendSlice = createSlice({
  name: 'friend',
  initialState: initialState,
  reducers: {
    setFriendData: (state, action: PayloadAction<Friend>) => {
      return {
        ...action.payload,
      };
    },
  },
});

export const { setFriendData } = friendSlice.actions;
export default friendSlice.reducer;
