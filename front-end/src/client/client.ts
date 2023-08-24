import axios, { AxiosError, AxiosResponse } from "axios";
import { persistor } from "../redux/store";

const readAccessToken = () => {
  const accessToken = localStorage.getItem("accessToken")
  return accessToken === null ? "" : accessToken
}
const readRefreshToken = () => {
  const refreshToken = localStorage.getItem("refreshToken")
  return refreshToken === null ? "" : refreshToken
}

const readMemberId = () => {
  const memberId = localStorage.getItem("memberId")
  return memberId === null ? "" : memberId
}

axios.defaults.baseURL = process.env.REACT_APP_BACKEND + 'dokkaebi/api'
axios.defaults.headers.post['Content-Type'] = 'application/json; charset=UTF-8';


const success = (response:AxiosResponse) => {return response}
const failure = async (error:AxiosError) => {
  // if access token is NOT valid
  if (error.response?.status === 401) {
    const config = error.config
    if (config === undefined) return
    // send refresh token in header to get new one
    return await axios.get("jwt/reissue", {headers:{"refreshToken":readRefreshToken()}})
      // if refresh token is valid
      .then(async (response) => {
        // save new tokens
        localStorage.setItem("accessToken", response.data.accessToken)
        localStorage.setItem("refreshToken", response.data.refreshToken)
        // do original work
        config.headers["accessToken"] = readAccessToken()
        return await axios(config)
      })
      // if refresh token is NOT valid
      .catch((error) => {
        alert("로그인 정보가 만료되었습니다. 다시 로그인해 주세요.")
        // delete all tokens
        localStorage.removeItem("accessToken")
        localStorage.removeItem("refreshToken")
        // delete persisted redux storage
        persistor.purge()
        // go to main page
        window.location.href = '/' 
        return Promise.reject(error)
      })
  } else {
    return Promise.reject(error)
  }
}

export const client = () => {
  const instance =  axios.create({
    headers: {
      "memberId": readMemberId()
    }
  })
  instance.interceptors.response.use(success, failure)
  return instance
}

export const clientWithToken = () => {
  const instance = axios.create({
    headers: {
      "memberId": readMemberId(),
      "Content-type": 'application/json; charset=UTF-8',
      "accessToken": readAccessToken(),
    }
  })
  instance.interceptors.response.use(success, failure)
  return instance
}