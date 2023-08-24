import { createSlice, PayloadAction } from '@reduxjs/toolkit'

export interface Invite {
	id?: number,
	receiverId: number,
	senderId: number,
	senderNickname?: string,
	studyRoomId?: number,
	studyRoomTitle?: string,
	notRead?: boolean
}

const initialState: Invite = {
	id: 0,
	receiverId: 0,
	senderId: 0,
	senderNickname: "",
	studyRoomId: 0,
	studyRoomTitle: "",
	notRead: true
}

const inviteSlice = createSlice({
    name: 'invite',
    initialState: initialState,
    reducers: {
				setInviteData: (state, action: PayloadAction<Invite>) => {
						return {
							...action.payload
						}
				}
    }
})

export const { setInviteData } = inviteSlice.actions
export default inviteSlice.reducer;