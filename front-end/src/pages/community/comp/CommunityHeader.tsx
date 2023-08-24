import React, { useEffect } from 'react';
import styles from '../../../styles/pages/community/comp/CommunityHeader.module.scss';
import communityBG from '../../../assets/communityBG.png';

import { Studyroom } from '../../../redux/reducers/studyroomReducer';

function CommunityHeader({ title, tab, curStudyRoom }: { title: string; tab: string; curStudyRoom?:Studyroom }) {

  return (
    <div className={styles.community}>
      <div className={styles.top}>
        {tab === 'community' ? (
          <div className={styles.black}>
            <img src={communityBG} alt="communityBG" className={styles.image}></img>
            <p className={styles.title}>{title}</p>
          </div>
        ) : (
          <div className={styles.black}>
            {/* studyroom 에서 headerImg 랑 title 불러와서 아래에 넣어주세요 */}
            <img src={curStudyRoom?.headerImg} alt="studyroom" className={styles.headerImg}></img>
            <p className={styles.title}>{curStudyRoom?.title}</p>
          </div>
        )}
      </div>
    </div>
  );
}

export default CommunityHeader;
