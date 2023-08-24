import React from 'react';
import styles from '../../../styles/pages/community/comp/StudySetting.module.scss';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/store';
import Chips from '../../../components/Chips';

function StudySetting() {
  const studyroom = useSelector((state: RootState) => state.persistedReducer.studyroomReducer);

  return (
    <div className={styles.StudySetting}>
      <p className={styles.midTitle}>스터디룸 제목</p>
      <p className={styles.title}>{studyroom.title}</p>
      <p className={styles.midTitle}>헤더 이미지</p>
      <img src={studyroom.headerImg} alt="헤더" className={styles.headerImg} />
      <p className={styles.midTitle}>카테고리</p>
      <div className={styles.chips}>
        {studyroom.tagNames !== undefined &&
          studyroom.tagNames.map((tag, index) => <Chips text={tag} key={index} />)}
      </div>
    </div>
  );
}

export default StudySetting;
