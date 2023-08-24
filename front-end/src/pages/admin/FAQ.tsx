import React, { useState } from 'react';
import styles from '../../styles/pages/admin/FAQ.module.scss';
import { Accordion, AccordionTab } from 'primereact/accordion';


const memberFAQ = [
  {
    title: '회원가입이 안돼요',
    content:
      '회원가입은 유효한 email 계정(@를 포함한 형식)과 대·소문자와 숫자를 포함한 8자리 이상의 문자열이어야 합니다. 또한 중복 닉네임 사용이 불가능합니다.',
  },
  {
    title: '도깨비봇 시리얼 넘버는 어디에 있나요?',
    content:
      '시리얼 넘버는 도깨비봇 하단 스티커에 적혀있습니다. 8자리의 문자열을 입력해주세요. 시리얼 넘버를 등록해야 원활한 서비스 사용이 가능합니다.',
  },
];
const communityFAQ = [
  {
    title: '스터디룸 생성이 안돼요',
    content:
      '스터디룸 이미지는 JPG, JPEG 만 등록이 가능합니다. 스터디룸 태그의 경우 입력 후 enter 를 입력해야 태그로 등록이 가능합니다.',
  },
  {
    title: '스터디룸을 삭제하고 싶어요',
    content:
      '스터디룸 삭제는 스터디룸을 생성한 방장만 삭제할 수 있습니다. 스터디룸의 설정 > 나가기 에서 삭제할 수 있습니다.',
  },
];
const profileFAQ = [
  {
    title: '아이콘 바꾸고싶어요',
    content:
      '기본 아이콘을 바꾸고 싶다면, 공부시간을 모아서 스토어에서 아이콘을 구매하실 수 있습니다. 구매한 아이콘은 마이페이지에서 수정할 수 있습니다.',
  },
  {
    title: '스마트 기기 연결을 해제하고 싶어요',
    content:
      '마이페이지>도깨비설정>스마트기기>편집 버튼을 누른 후 연결 해제 버튼을 누르시면 연결이 해제됩니다.',
  },
];
const alertFAQ = [
  {
    title: '채팅방 알림이 안 사라져요',
    content:
      '채팅방 알림은 채팅 메시지에 답장을 해야 사라집니다.',
  },
  {
    title: '친구 초대 알림은 어디서 볼 수 있나요?',
    content:
      '친구 초대 및 스터디룸 초대 알림은 상단바의 이름과 아이콘에 마우스를 올리면 확인할 수 있습니다. 수락 또는 거절 버튼을 이미 누른 경우 다시 확인이 불가하며, 다시 초대를 받아야합니다.',
  },
];
let totalFAQ:any[] =  memberFAQ.concat(communityFAQ, profileFAQ, alertFAQ);

function FAQ() {
  const [currentTab, clickTab] = useState(0);

  const tabMenu = [
    { name: '전체', content: totalFAQ},
    { name: '계정 관리', content: memberFAQ },
    { name: '커뮤니티', content: communityFAQ },
    { name: '프로필 설정', content: profileFAQ },
    { name: '알림 설정', content: alertFAQ },
  ];

  const clickTabHandler = (now: number) => {
    clickTab(now);
  };

  return (
    <div className={styles.faq}>
      <p className={styles.title}>자주 묻는 질문</p>
      <div className={styles.total}>
        <div className={styles.left}>
          <div className={styles.tabDiv}>
            {tabMenu.map((menu, now) => (
              <div
                className={now === currentTab ? styles.now : styles.next}
                onClick={() => clickTabHandler(now)}
                key={menu.name}
              >
                {menu.name}
              </div>
            ))}
          </div>
        </div>
        <div className={styles.right}>
          <h1 className={styles.content}>{tabMenu[currentTab].name}</h1>
          <Accordion>
            {Object.values(tabMenu[currentTab].content).map((state, index) => (
              <AccordionTab header={state.title} key={index}>
                <p>{state.content}</p>
              </AccordionTab>
            ))}
          </Accordion>
        </div>
      </div>
    </div>
  );
}

export default FAQ;
