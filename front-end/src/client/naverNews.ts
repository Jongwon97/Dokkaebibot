import axios, { AxiosError, AxiosResponse } from 'axios';

export const searchNaverNews = (tag: string) => {
  // const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
  // const CLIENT_SECRET = process.env.REACT_APP_CLIENT_SECRET;

  // axios
  //   .get('https://openapi.naver.com/v1/search/news.json', {
  //     params: {
  //       query: tag,
  //       display: 5, // 검색 결과 노출 개수
  //     },
  //     headers: {
  //       'X-Naver-Client-Id': CLIENT_ID,
  //       'X-Naver-Client-Secret': CLIENT_SECRET,
  //     },
  //   })
  //   .then((response) => {
  //     const { data } = response;
  //     // 클라이언트에 보내기
  //     //   res.send(data.items);
  //   })
  //   .catch((error) => {
  //     let message = 'Unknown Error';
  //     if (error instanceof Error) message = error.message;
  //     console.log(message);
  //   });
};
