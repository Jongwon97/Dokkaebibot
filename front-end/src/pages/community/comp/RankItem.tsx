import React from 'react';
import styles from '../../../styles/pages/community/comp/RankItem.module.scss';
import left from '../../../assets/left_rank.png';
import right from '../../../assets/right_rank.png';
import { getIcon } from '../../../client/members';
import { Ranker } from '../../../client/analysis';

const RankItem = ({type, ranker}: {type:string, ranker:Ranker}) => {
  return (
    <div className={styles.div}>
        <div className={styles.rankTitle}>{type}</div>
        <div className={styles.time}>{ranker?.timeSum}</div>
        <div className={styles.image}>
            <img src={left} alt='left' className={styles.wings} />
            <div className={styles.member}>
                <img className={styles.icon} src={getIcon(ranker?.iconNumber)} alt='memberIcon'/>
                <p  className={styles.nickname}>{ranker?.nickname}</p>
            </div>
            <img src={right} alt='right' className={styles.wings} />
        </div>
    </div>
  )
}

export default RankItem;
