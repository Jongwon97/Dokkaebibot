import { client, clientWithToken } from "./client";

const adminURL = 'admin/'

export const postNotice = (data:object) => {
    return clientWithToken().post(adminURL + 'notice/check', data)
}

export const getNoticeDetail = (id:string) => {
	return client().get(adminURL + 'notice/' + id)
}

export const deleteNotice = (id:string) => {
	return clientWithToken().delete(adminURL + 'notice/' + id + '/check')
}

export const updateNotice = (data:object) => {
	return clientWithToken().put(adminURL + 'notice/check', data)
}

export const getNoticeList = () => {
	return client().get(adminURL + 'notice/all')
}

export const getQnaList = () => {
	return client().get(adminURL + 'qna/all')
}

export const postQna = (data:object) => {
	return clientWithToken().post(adminURL + 'qna/check', data)
}

export const deleteQna = (id:string) => {
	return clientWithToken().delete(adminURL + 'qna/' + id + '/check')
}