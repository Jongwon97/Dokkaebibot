import React from 'react';
import styles from '../../../styles/pages/members/comp/StudySetting.module.scss';
import StudyCalendar from '../../study/comp/StudyCalendar';

function StudySetting() {
  return (
    <div className="StudySetting">
      <div className={styles.basicSetting}>
        <StudyCalendar />
      </div>
    </div>
  );
}

export default StudySetting;
