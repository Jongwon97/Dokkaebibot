import React, { useEffect, useRef, useState } from 'react';
import styles from '../../../styles/pages/community/comp/StudyroomChat.module.scss';
import { useForm, SubmitHandler } from 'react-hook-form';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/store';
import { InputTextarea } from 'primereact/inputtextarea';
import { Button } from 'primereact/button';
import { getChats } from '../../../client/studyroom';

import stompjs from "stompjs"
import SockJS from "sockjs-client"

const StudyroomChat = () => {
  
  const userId = useSelector((state: RootState) => state.persistedReducer.memberReducer.id);
  const userNickName = useSelector((state: RootState) => state.persistedReducer.memberReducer.nickname)
  const [chatList, setChatList] = useState<chatDto[]>([])

  const roomId = Number(localStorage.getItem('roomId')); // 채팅방 number
  const roomTitle=localStorage.getItem("roomTitle");

  const scrollRef = useRef<HTMLDivElement>(null)


  // 이전 메세지 가져오기
  const fetchData = async () => {
      try {
        const response = await getChats({roomId});
        setChatList(response.data);
      } catch (error) {
        console.error('Error fetching chat data:', error);
      }
  };
  // 웹 소켓 설정
  const sockJS = new SockJS(process.env.REACT_APP_BACKEND + "dokkaebi/websocket-endpoint");
  const stompClient = stompjs.over(sockJS);

  // 소켓 처리
  useEffect(() => {

    fetchData();

    stompClient.connect({},()=>{
      stompClient.subscribe("/sub/chat/"+roomId, (data)=>{
        const message = JSON.parse(data.body);
          const newChat: chatDto = {
            roomId: message.roomId,
            senderId: message.senderId,
            senderNickName: message.senderNickName,
            message: message.message,
            time: message.time
          }
          setChatList((prevChatList) => [...prevChatList, newChat]);
      })
    })

  }, [])

  useEffect(() => {
    if (scrollRef.current !== null) {
      scrollRef.current.scrollTop = scrollRef.current.scrollHeight
    }
  }, [chatList.length])

  // 메시지 기본 value
  interface chatDto {
    roomId: number,
    senderId: number,
    senderNickName: string,
    message: string,
    time: string
  }
  
  const { register, handleSubmit } = useForm<chatDto>();
  
  const [ messageContent, setMessageContent] = useState<string>(''); // 메세지 입력칸 데이터

  // 메세지 전송 버튼 클릭시
  const onSubmitHandler: SubmitHandler<chatDto> = (data: chatDto) => {
    if (messageContent.trim() === "") {
      return; // 빈 메시지는 전송하지 않음
    }
    const TIME_ZONE = 9 * 60 * 60 * 1000;
    const chatDto: chatDto = {
      roomId: roomId,
      senderId: userId,
      senderNickName: userNickName,
      message: messageContent.trim(),
      time: new Date(new Date().getTime() + TIME_ZONE).toISOString(),
    };
  
    // WebSocket을 통해 서버로 메시지 전송
    stompClient.send("/pub/sendMessage", {}, JSON.stringify(chatDto));
    setMessageContent("");
  };


  return (
    <div className={styles.chatDiv}>
      <div className={styles.header}>
        <p className={styles.title}>{roomTitle}</p>
        <p className={styles.sub}>스터디룸 멤버들과 이야기 나눠보세요</p>
      </div>
      <div className={styles.content} ref={scrollRef}>
        {chatList.map((chat, index) => (
          <div key={index} className={styles.message}>
            { chat.senderId !== userId && <p className={styles.nickname}>{chat.senderNickName}</p>}
            <div className={chat.senderId === userId ? styles.rightDiv : styles.leftDiv}>
              <span className={chat.senderId === userId ? styles.right : styles.left}>
                {chat.message}
              </span>
              <span className={styles.time}>{chat.time.replace('T', ' ').substring(11, 16)}</span>
            </div>
          </div>
        ))}
      </div>
      <div className={styles.footer}>
        <form onSubmit={handleSubmit(onSubmitHandler)} className={styles.form}>
          <InputTextarea
            {...register('message')}
            value={messageContent}
            onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => setMessageContent(e.target.value)}
            rows={2}
            className={styles.textArea}
            placeholder="메시지를 입력해주세요"
          />
          <Button icon="pi pi-send" type="submit" className={styles.inputButton} />
        </form>
      </div>
    </div>
  );
};

export default StudyroomChat;
