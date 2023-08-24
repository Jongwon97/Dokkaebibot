import { client, clientWithToken } from "./client"
import { Article } from "../redux/reducers/articleReducer"

const articleURL = 'community/article/'

export const getPopular = (start:Number, num:Number) => {
    return clientWithToken().get(articleURL + 'popular/' + start + "/" + num)
}
export const getNew = (start:Number, num:Number) => {
    return clientWithToken().get(articleURL + 'new/' + start + "/" + num)
}

export const getDetail = (id:String) => {
    return clientWithToken().get(articleURL + id + '/zzz')
}

export const postArticle = (article: Article) => {
    return clientWithToken().post(articleURL + 'create/check', article)
}

export const deleteArticle = (id: Number|undefined) => {
    return clientWithToken().delete(articleURL + id + "/check")
}

export const updateArticle = (article: Article) => {
    return clientWithToken().put(articleURL + article.id + "/check", article)
}

// post like and scrap

// export const postLike = ((articleId: String) => {
// 	return clientWithToken().post('community/like/' + articleId + '/check')
// })

// export const deleteLike = ((articleId: String) => {
// 	return clientWithToken().delete('community/like/' + articleId + '/check')
// })

// export const postScrap = ((articleId: String) => {
// 	return clientWithToken().post('community/scrap/' + articleId + '/check')
// })

// export const deleteScrap = ((articleId: String) => {
// 	return clientWithToken().delete('community/scrap/' + articleId + '/check')
// })

export const toggleLike = (articleId: string) => {
    return clientWithToken().post('community/like/' + articleId + '/check')
}

export const toggleScrap = (articleId: string) => {
    return clientWithToken().post('community/scrap/' + articleId + '/check')
}