import React from 'react';
import styles from '../../../styles/pages/study/comp/StudyAnalysis.module.scss';
import card01 from '../../../assets/card01.png';
import card02 from '../../../assets/card02.png';
import card03 from '../../../assets/card03.png';
import card04 from '../../../assets/card04.png';

function StudyAnalysis() {
  const isTime = false;
  return (
    <div className={styles.poseState}>
      <p className={styles.title}>분석 리포트</p>
      <div className={styles.cards}>
        {isTime ? (
          <>
            <img src={card01} alt="분석" className={styles.card} />
            <img src={card03} alt="분석" className={styles.card} />
            <img src={card02} alt="분석" className={styles.card} />
            <img src={card04} alt="분석" className={styles.card} />
          </>
        ) : (
          <div>분석할 공부 기록이 아직 없어요 :(</div>
        )}
      </div>
    </div>
  );
}

export default StudyAnalysis;
