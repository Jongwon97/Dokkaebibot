import { client, clientWithToken } from "./client"
import { Invite } from "../redux/reducers/inviteReducer"

const inviteUrl = 'community/invite/'


export const postFriendInvite = (senderId:number, receiverEmail:string) => {
	return clientWithToken().post(inviteUrl + "check", {
		"receiverEmail": receiverEmail,
		"senderId": senderId
	})
}

export const postStudyroomInvite = (senderId:number, receiverEmail:string, roomId:string) => {
	return clientWithToken().post(inviteUrl + "check", {
		"receiverEmail": receiverEmail,
		"senderId": senderId,
		"studyRoomId": roomId
	})
}

export const getReceivedInvite = () => {
	return clientWithToken().get(inviteUrl + "received/check")
}

export const deleteInvite = (inviteId:number) => {
	return clientWithToken().delete(inviteUrl + inviteId + "/check")
}