import React from 'react';
import styles from '../../../styles/pages/community/comp/StudyMemberSetting.module.scss';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/store';
import { getIcon } from '../../../client/members';
import StudyroomInvite from './StudyroomInvite';

function StudyMemberSetting() {
  const studyroom = useSelector((state: RootState) => state.persistedReducer.studyroomReducer);
  const studyroomMembers = useSelector(
    (state: RootState) => state.persistedReducer.StudymemberReducer,
  );

  return (
    <div className={styles.StudyMemberSetting}>
      <>
        <p className={styles.midTitle}>친구 초대하기</p>
        <p className={styles.invite}><StudyroomInvite /></p>
      </>
      <div className={styles.second}>
        <div className={styles.max}>
          <p className={styles.midTitle}>최대 인원 수</p>
          <p className={styles.component}>{studyroom.maxCapacity}</p>
        </div>
        <div className={styles.cur}>
          <p className={styles.midTitle}>현재 인원 수</p>
          <p className={styles.component}>{studyroom.curAttendance}</p>
        </div>
      </div>
      <>
        <p className={styles.midTitle}>스터디원</p>
        <div className={styles.memberList}>
          {Object.values(studyroomMembers).map((member, index) => (
            <div className={styles.member} key={index}>
              <img src={getIcon(member.iconNumber)} alt="memberIcon" className={styles.icon} />
              <p>{member.nickname}</p>
            </div>
          ))}
        </div>
      </>
    </div>
  );
}

export default StudyMemberSetting;
