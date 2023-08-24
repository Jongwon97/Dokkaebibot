import React from 'react';
import styles from '../../styles/pages/home/Home.module.scss';
import homeKkaebi from '../../assets/homeBGkkaebi.png';
import { Link } from 'react-router-dom';
import { Button } from 'primereact/button';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import defaultBG from '../../assets/defaultBG.jpg';
import HomeStudy from './comp/HomeStudy';
import ConnectBot from './comp/ConnectBot';
import AboutDokkaebi from './comp/AboutDokkaebi';

function Home() {
  const user = useSelector((state: RootState) => state.persistedReducer.memberReducer);

  return (
    <div className={styles.homeDiv}>
      {user.id !== 0 && localStorage.getItem('accessToken') ? (
        <>
          {user.haveDevice === false ? (
            <ConnectBot />
          ) : (
            <div className={styles.studyDiv}>
              <div className={styles.loggedIn}>
                <div className={styles.left}>
                  <h1 className={styles.title}>
                    어제보다 더 나은 <br /> 오늘을 위해 파이팅!
                  </h1>
                </div>
                <img src={defaultBG} alt="default 배경" className={styles.defaultBG} />
              </div>
              <HomeStudy id={user.id} />
            </div>
          )}
        </>
      ) : (
        <>
          <div className={styles.beforeLogin}>
            <div className={styles.left}>
              <h1 className={styles.title}>
                언제 어디서나 <br /> 일정한 공부 습관, <br /> <span>도깨비 봇</span>과 함께
              </h1>
              <Link to={'members/register'}>
                <Button label="회원가입" className={styles.signup} />
              </Link>
              <p>
                이미 우리 스터디원이신가요?{' '}
                <Link to={'/members/login'} className={styles.link}>
                  로그인
                </Link>
                하기
              </p>
            </div>
            <img src={homeKkaebi} alt="트랙배경" className={styles.trackBG} />
          </div>
          <AboutDokkaebi />
        </>
      )}
    </div>
  );
}

export default Home;
