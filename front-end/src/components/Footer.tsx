import React from 'react';
import logo_black from '../assets/logo_black.png';
import styles from '../styles/components/Footer.module.scss';
import classNames from 'classnames/bind';
import { Link } from 'react-router-dom';

const cx = classNames.bind(styles);

const Footer = () => {
  return (
    <>
      <footer className={styles.footer}>
        <Link to={'/'} className={styles.logoLink}>
          <img src={logo_black} alt="logo" height={'17px'} className={styles.logo} />
          <p className={styles.logoText}>
            도깨비봇 <span className="ml-3"> | </span>
          </p>
        </Link>

        <div className={cx(styles.router)}>
          <Link to={'/about'} className={styles.routerEach}>
            서비스 소개
          </Link>
          <Link to={'/notice'} className={styles.routerEach}>공지사항</Link>
          <Link to={'/privacy'} className={styles.routerEach}>개인정보처리방침</Link>
          <Link to={'/faq'} className={styles.routerEach}>자주묻는질문</Link>
          <Link to={'/qna'} className={styles.routerEach}>문의하기</Link>
        </div>
      </footer>
    </>
  );
};

export default Footer;
