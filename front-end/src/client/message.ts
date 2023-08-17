import { client, clientWithToken } from "./client"
const messageURL = 'community/message/'

export const getAndReadMessageFromFriend = (friendId: number) => {
	return clientWithToken().get(messageURL + "received/" + friendId + "/check")
}

export const sendMessage = (data: {content: string, senderId: number, receiverId: number}) => {
	return clientWithToken().post(messageURL + "check", data)
}