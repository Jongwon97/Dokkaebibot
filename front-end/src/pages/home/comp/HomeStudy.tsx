import React from 'react';
import styles from '../../../styles/pages/home/comp/HomeStudy.module.scss';
import StudyCalendar from '../../study/comp/StudyCalendar';
import StudyroomList from '../../community/comp/StudyroomList';

function HomeStudy({ id }: { id: number }) {
  const nowMonth = new Date().getMonth() + 1;
  return (
    <>
      {id !== 0 && localStorage.getItem('accessToken') && (
        <div className={styles.homeStudy}>
          <div className={styles.time}>
            <p>한 눈에 확인하는 나의 공부 시간 ⏰</p>
            <p className={styles.title}>나의 공부 분석</p>
            <div className={styles.calDiv}>
              <p>8월</p>
              <StudyCalendar month={nowMonth} />
            </div>
          </div>
          <div className={styles.studyRoom}>
            <p>같이하면 능률이 두 배 🙌</p>
            <p className={styles.title}>나의 스터디 룸</p>
            <div className={styles.myList}>
              <StudyroomList title="" value="1" />
            </div>
          </div>
        </div>
      )}
      {/* <div className={styles.news}>
        <p>잠시 쉬어가요💡</p>
        <p className={styles.title}>김싸피님을 위한 추천 아티클</p>
        <div className={styles.newsItem}>뉴스 아이템</div>
      </div> */}
    </>
  );
}

export default HomeStudy;
