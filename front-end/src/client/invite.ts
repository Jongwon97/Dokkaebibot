import { client, clientWithToken } from "./client"
import { Invite } from "../redux/reducers/inviteReducer"

const inviteUrl = 'community/invite/'


export const postInvite = (invite:Invite) => {
	return clientWithToken().post(inviteUrl + "check", invite)
}

export const getReceivedInvite = () => {
	return clientWithToken().get(inviteUrl + "received/check")
}

export const deleteInvite = (inviteId:number) => {
	return clientWithToken().delete(inviteUrl + inviteId + "/check")
}