import React from 'react';
import styles from '../../../styles/pages/study/comp/StudyState.module.scss';
import classNames from 'classnames/bind';
import { Tooltip } from 'primereact/tooltip';
import state05 from '../../../assets/dokkaebi_study.png';
import state02 from '../../../assets/dokkaebi_phone.png';
import state03 from '../../../assets/dokkaebi_sleep.png';
import state04 from '../../../assets/dokkaebi_away.png';
import state06 from '../../../assets/dokkaebi_bad.png';
import needLoginImg from '../../../assets/need_login.png';
import { Link } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/store';

interface BarProps {
  width: number;
}

const Bar: React.FC<BarProps> = ({ width }) => {
  const cx = classNames.bind(styles);
  const barStyle = {
    '--bar-width': `${width * 1.5}%`,
  } as React.CSSProperties;

  return (
    <>
      <Tooltip target=".target" />
      <div
        className={cx('target', width < 33 ? styles.bar : styles.barDark)}
        data-pr-tooltip={`${width}%`}
        style={barStyle}
      />
    </>
  );
};

function StudyState({
  poseTimeSum,
}: {
  poseTimeSum:
    | undefined
    | {
        sleep: number;
        away: number;
        bad: number;
        phone: number;
        good: number;
      };
}) {
  const dateToString = (timestamp: number) => {
    const timeZoneOffset = new Date(0).getTimezoneOffset();
    const fixedDate = new Date(timestamp + 60000 * timeZoneOffset);
    return (
      ('0' + fixedDate.getHours()).slice(-2) +
      ':' +
      ('0' + fixedDate.getMinutes()).slice(-2) +
      ':' +
      ('0' + fixedDate.getSeconds()).slice(-2)
    );
  };

  const totalValue = poseTimeSum
    ? poseTimeSum.good + poseTimeSum.bad + poseTimeSum.away + poseTimeSum.sleep + poseTimeSum.phone
    : 0;

  const total = dateToString(totalValue);
  const realTime = dateToString(poseTimeSum ? poseTimeSum.good + poseTimeSum.bad : 0);

  const barData: number[] = [
    poseTimeSum ? Math.floor((poseTimeSum.good / totalValue) * 100) : 0,
    poseTimeSum ? Math.floor((poseTimeSum.bad / totalValue) * 100) : 0,
    poseTimeSum ? Math.floor((poseTimeSum.phone / totalValue) * 100) : 0,
    poseTimeSum ? Math.floor((poseTimeSum.sleep / totalValue) * 100) : 0,
    poseTimeSum ? Math.floor((poseTimeSum.away / totalValue) * 100) : 0,
  ];
  const user = useSelector((state: RootState) => state.persistedReducer.memberReducer);

  return (
    <div className={styles.studyState}>
      {user.id !== 0 ? (
        <>
          <p className={styles.title}>누적 공부 상태 변화</p>
          <div className={styles.timeDiv}>
            <div className={styles.dateDiv}>
              <p className={styles.top}>총 기록시간</p>
              <p className={styles.big}>{total}</p>
            </div>
            <div className={styles.dateDiv}>
              <p className={styles.top}>순 공부시간</p>
              <p className={styles.big}>{realTime}</p>
            </div>
          </div>

          <div className={styles.study}>
            <div className={styles.studyDiv}>
              <img src={state05} alt="공부중" className={styles.icon} />
              <Bar width={barData[0]} />
            </div>
            <div className={styles.studyDiv}>
              <img src={state06} alt="나쁜자세" className={styles.icon} />
              <Bar width={barData[1]} />
            </div>
            <div className={styles.studyDiv}>
              <img src={state02} alt="폰하는중" className={styles.icon} />
              <Bar width={barData[2]} />
            </div>
            <div className={styles.studyDiv}>
              <img src={state03} alt="자는중" className={styles.icon} />
              <Bar width={barData[3]} />
            </div>
            <div className={styles.studyDiv}>
              <img src={state04} alt="자리비움" className={styles.icon} />
              <Bar width={barData[4]} />
            </div>
          </div>
        </>
      ) : (
        <div className={styles.login}>
          <img src={needLoginImg} alt="로그인 해주세요" width="128px" height="128px" />
          <p>
            <Link to={'/members/login'} className={styles.link}>
              로그인
            </Link>
            이 필요한 서비스입니다
          </p>
        </div>
      )}
    </div>
  );
}

export default StudyState;
