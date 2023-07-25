import axios from "axios";


axios.defaults.baseURL = 'http://localhost:8080/dokkaebi/api'
axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';

let token = localStorage.getItem("accessToken")

export const client = axios.create({})

export const clientWithToken = axios.create({
  headers: {
    "Content-type": 'application/json; charset=UTF-8',
    "accessToken": token === null ? "" : token,
  }
})