import { client, clientWithToken } from "./client"
import { Comment } from "../redux/reducers/commentReducer"
const articleURL = 'community/comment/'

export const getFromArticle = (articleId: Number) => {
	return client().get(articleURL + articleId)
}

export const postComment = (articleId: Number, comment: Comment) => {
	return clientWithToken().post(articleURL + articleId + "/check", comment)
}