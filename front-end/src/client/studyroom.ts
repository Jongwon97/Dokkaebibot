import axios from "axios"


let token = localStorage.getItem("accessToken")

token = token === null ? "" : token
const baseURL = 'http://localhost:8080/dokkaebi/api/studyrooms'
// const headers = {
//     "Content-type": 'application/json; charset=UTF-8',
//     "accessToken": token,
// }

export const getStudyRoomsLatest = () => {
    return axios.get(baseURL + '/community')
}