# <img src='./front-end/src/assets/logo.png'  height="24"/> 도깨비봇

## <img src='./front-end/src/assets/dokkaebi.png'  height="24"/> 올바른 공부 습관을 위한 분석 서비스 <img src='./front-end/src/assets/dokkaebi.png'  height="24"/>
### 웹사이트 : [도깨비봇](https://i9a302.p.ssafy.io)
도깨비봇은 '<strong>도</strong>와주고 <strong>깨</strong>워주는 공부 <strong>비</strong>서 로<strong>봇</strong>'의 줄임말로, 시간과 장소의 제약 없이 혼자 있는 공간에서도 효율적으로 공부할 수 있도록 도와주는 웹IoT 서비스입니다.

### 메인 기능
- 도깨비봇(라즈베리파이)이 카메라로 <b>현재 상태 분석</b>
- 웹사이트에 업데이트 된 <b>공부 분석 차트 확인</b>
- 여러 사람과 함께하는 비대면 <b>온라인 스터디 플랫폼</b>

- - -

## 🎬 시연 영상 및 UCC

### 프로젝트 UCC
<a href="https://youtu.be/97-a894fMSg"><img src="https://blog.kakaocdn.net/dn/c2yJ7I/btqwXeUM6jI/a3WrMGPo9vakaDzQWepkOK/img.jpg"  width="140"/></a>

### 시연 영상
<a href="https://youtu.be/U33sC4tZ6yI?si=4dx1aQ8cJb6m-Obv"><img src="https://blog.kakaocdn.net/dn/c2yJ7I/btqwXeUM6jI/a3WrMGPo9vakaDzQWepkOK/img.jpg"  width="140"/></a>

## 🔑 핵심 기능

### IOT

- Mediapipe 얼굴인식 인공지능 모델 활용하여 Face mesh data로 Empty, Sleep, Studying 상태 판단 
- Mediapipe pose landmark detection으로 추출한 data와 사이킷런을 활용한 자세 판별 모델 생성
- PICO voice porcupine 음성인식 모델 활용 및 Wake word detection 기술 사용
- MQTT를 활용한 Web - IoT 통신

### Back-end

- Spring Boot를 활용한 REST기반 어플리케이션 개발
- MQTT를 활용한 Web-IoT 통신
- Stomp를 활용한 실시간 채팅 구현
- Stomp를 활용한 실시간 유저 접속 여부, 접속 시간 구현
- Stomp, MQTT를 활용한 IoT-Backend-Frontend 간 실시간 통신 구현- aws s3를 활용한 이미지 저장 관리
- JWT를 활용한 로그인 구현 및 구글, 카카오 로그인 api 구현

### Front-end

- React 와 Scss 를 활용한 웹 어플리케이션 개발(SPA)
- state 관리 및 데이터 전달 활용을 위한 React-redux 사용
- TypeScript 를 활용한 타입 오류 및 가독성 관리
- Chart.js 를 활용한 데이터 시각화 그래프 구현
- Stomp를 활용한 웹 소켓 구현

- - - 

## 📆 제작 기간 및 인원
제작 기간 : 2023. 07.04 ~ 2023. 08. 18 (7주) <br/>
참여 인원 : 6인

### IoT
### 👨‍💻 홍의선 (팀장) : face detection, object detection
### 👨‍💻 류병민 : voice, atomosphere
### 👩‍💻 이효정 : pose detection
### Web
### 👨‍💻 이종원 : Back-end, web socket
### 👨‍💻 김동훈 : Back-end, Front-end, CI/CD
### 👩‍💻 조원주 : Front-end, Design

- - - 

## 📚 사용한 기술


### Backend
- Spring Boot
- JPA
- Jwt
- Stomp
- MQTT
- aws s3
- Mysql
- webSocket

### Frontend
- Node.js
- typescript 4.9.5
- React 18.2.0
- React-redux 8.1.1
- Sass(Scss) 8.0.0
- chart.js 4.3.3
- Stomp 7.0.0

### Raspberry PI
- Python
- OpenCV
- MQTT
- Scikit learn
- Mediapipe
- PicoVoice
- Google Cloud Speech API(STT)
- YOLOv3-tiny
- coco dataset

### CI/CD
- Jenkins
- Gitlab Webhooks
- Docker
- Docker-compose

### Server
- Nginx
- Mosquitto
- Docker

- - - 

## 🔎 프로젝트 구성

### ⚙ [포팅매뉴얼](./exec/PORTING%20MANUAL.pdf)
### 🗃 [ERD](./exec/ERD.jpg)
### 🔗[시스템 모식도](./exec/SYSTEM_LOGIC.jpg)
### 🖼 [기능명세](./exec/도깨비봇_기능명세.png)
