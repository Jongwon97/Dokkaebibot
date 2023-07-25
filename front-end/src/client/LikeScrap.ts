import { clientWithToken } from "./client";

export const toggleLike = ((articleId: String) => {
	return clientWithToken.post('community/like/' + articleId + '/check')
})

export const toggleScrap = ((articleId: String) => {
	return clientWithToken.post('community/scrap/' + articleId + '/check')
})