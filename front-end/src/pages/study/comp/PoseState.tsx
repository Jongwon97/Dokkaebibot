import React from 'react';
import styles from '../../../styles/pages/study/comp/PoseState.module.scss';
// import DataGraph from './DataGraph';

function PoseState(dates:Date[]) {
  return (
    <div className={styles.poseState}>
        <p className={styles.title}>오늘의 공부 분석</p>
        {/* <DataGraph dates={dates}/> */}
    </div>
  );
}

export default PoseState;