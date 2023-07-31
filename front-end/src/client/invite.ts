import { client, clientWithToken } from "./client"
import { Invite } from "../redux/reducers/inviteReducer"

const inviteUrl = 'community/invite/'

export const postInvite = (invite:Invite) => {
	console.log("post invite sended")
	return clientWithToken.post(inviteUrl + "check", invite)
}