import React from 'react';
import { useNavigate } from 'react-router-dom';
import notFoundImg from '../assets/NotFound.png';
import styles from '../styles/components/NotFound.module.scss';

export default function NotFound() {
  let navigate = useNavigate();
  let goBack = () => {
    navigate(-1);
  };

  return (
    <div className={styles.notFound}>
      <img src={notFoundImg} alt="notfound" className={styles.img} />
      <p className={styles.title}>Page Not Found</p>
      <p className={styles.content}>요청하신 페이지를 찾을 수 없습니다.</p>
      <div className={styles.backButton} onClick={goBack}>
        이전 페이지로 돌아가기
      </div>
    </div>
  );
}
