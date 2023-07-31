import { client, clientWithToken } from "./client"

const friendUrl = 'community/friend/'

export const postFriend = (friendId:Number) => {
	console.log("done")
	return clientWithToken.post(friendUrl + friendId + "/check")
}

export const getFriend = () => {
	return clientWithToken.get(friendUrl + "check")
}

export const deleteFriend = (friendId:Number) => {
	return clientWithToken.delete(friendUrl + friendId + "/check")
}