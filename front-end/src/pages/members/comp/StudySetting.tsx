import React from 'react';
import styles from '../../../styles/pages/members/comp/StudySetting.module.scss';
import StudyCalendar from '../../study/comp/StudyCalendar';
import StudyAnalysis from '../../study/comp/StudyAnalysis';

function StudySetting() {
  // 이번 달만 보여줌ㅎㅎ
  let nowMonth = new Date().getMonth() + 1;

  return (
    <div className="StudySetting">
      <div className={styles.basicSetting}>
        <p>일별 공부 시간</p>
        <StudyCalendar month={nowMonth} />
      </div>
      <StudyAnalysis />
    </div>
  );
}

export default StudySetting;
