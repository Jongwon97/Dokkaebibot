import { client, clientWithToken } from "./client"
import { Comment } from "../redux/reducers/commentReducer"

const commentURL = 'community/comment/'

export const getFromArticle = (articleId: Number) => {
	return client().get(commentURL + articleId)
}

export const postComment = (articleId: Number, comment: Comment) => {
	return clientWithToken().post(commentURL + articleId + "/check", comment)
}

export const deleteComment = (commentId: number) => {
	return clientWithToken().delete(commentURL + commentId + "/check")
}