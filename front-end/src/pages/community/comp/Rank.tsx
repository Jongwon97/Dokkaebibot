import React, { useState, useEffect, useRef } from 'react';
import styles from '../../../styles/pages/community/comp/Rank.module.scss';
import RankItem from './RankItem';
import { getRanker } from '../../../client/analysis';
import { Ranker } from '../../../client/analysis';

const intial = [
  {
    nickname: 'first',
    iconNumber: 0,
    timeSum: '01:23:45'
  },
  {
    nickname: 'first',
    iconNumber: 0,
    timeSum: '01:23:45'
  },
  {
    nickname: 'first',
    iconNumber: 0,
    timeSum: '01:23:45'
  }
]


const Rank = () => {
  const [rankers, setRankers] = useState<Ranker[]>(intial)

  useEffect(() => {
    getRanker().then((response) => {
      if (response) {
        setRankers([
          response.data.total,
          response.data.net,
          response.data.pose,
        ])
      }
    })
  }, [])

  const types = ['누적 공부 시간 ⏰', '순 공부 시간 🥇', '바른 자세 왕 🧘‍♂️']

  return (
    <div className={styles.div}>
      <div className={styles.rank}>
        {rankers.map ((ranker, index) => (
          <div key={index} className={styles.rankBox}>
            <RankItem type={types[index]} ranker={ranker} />
          </div>
        ))}
      </div>
      {/* <div className={styles.rankMobile}>
        <div className={styles.rankBox}><RankItem member={membersArray[0]} /></div>
      </div> */}
    </div>
  )
}

export default Rank;
