import React, { useRef } from 'react';
import { Link, NavLink } from 'react-router-dom';
import styles from '../styles/components/Header.module.scss';
import classNames from 'classnames/bind';
import logo from '../assets/logo.png';
import { Button } from 'primereact/button';
import { OverlayPanel } from 'primereact/overlaypanel';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import HeaderHover from './HeaderHover';
import SidebarList from './Sidebar';
import { getIcon } from '../client/members';

const cx = classNames.bind(styles);

const Header = () => {
  const activeNav = {
    fontWeight: 800,
  };

  const user = useSelector((state: RootState) => state.persistedReducer.memberReducer);

  const overlay = useRef<OverlayPanel>(null);

  const loggedIn = () => {
    return user.id !== 0 && localStorage.getItem('accessToken')
  }

  return (
    <>
      <header className={styles.header}>
        <Link to={'/'}>
          <img src={logo} alt="logo" height={'35px'} />
        </Link>
        <div className={cx(styles.nav)}>
          <NavLink
            to={'/study'}
            style={({ isActive }) => (isActive ? activeNav : undefined)}
            className={styles.navText}
          >
            공부
          </NavLink>
          <NavLink
            to={'/community'}
            style={({ isActive }) => (isActive ? activeNav : undefined)}
            className={styles.navText}
          >
            커뮤니티
          </NavLink>
          <NavLink
            to={'/store'}
            style={({ isActive }) => (isActive ? activeNav : undefined)}
            className={styles.navText}
          >
            스토어
          </NavLink>
        </div>
        {loggedIn() ? (
          <div
            className={styles.loggedIn}
            onClick={(e) => {
              overlay.current?.toggle(e);
            }}
          >
            <p className={styles.userName}>안녕하세요, {user.nickname}님</p>
            <img src={getIcon(user.iconNumber)} alt="프로필이미지" width="42px" height="42px" />
            <OverlayPanel ref={overlay}>
              <HeaderHover></HeaderHover>
            </OverlayPanel>
          </div>
        ) : (
          <div className={cx(styles.buttons)}>
            <Link to={'/members/login'}>
              <p className={styles.signin}>
                <span>로그인</span>
              </p>
            </Link>
            <Link to={'members/register'}>
              <Button label="회원가입" className={styles.signup} />
            </Link>
          </div>
        )}
        <div className={cx(styles.hidden)}>
          <SidebarList />
        </div>
      </header>
    </>
  );
};

export default Header;
