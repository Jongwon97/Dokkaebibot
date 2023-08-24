import React, { useState } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import styles from '../styles/components/Sidebar.module.scss';
import classNames from 'classnames/bind';
import { Button } from 'primereact/button';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../redux/store';
import { Sidebar } from 'primereact/sidebar';
import AlertMessage from './Alert';
import { logoutAction } from '../redux/reducers/memberReducer';
import { memberLogout } from '../client/members';
import { getIcon } from '../client/members';

const cx = classNames.bind(styles);

const SidebarList = () => {
  const [visibleRight, setVisibleRight] = useState<boolean>(false);

  const user = useSelector((state: RootState) => state.persistedReducer.memberReducer);

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const logout = () => {
    memberLogout().catch((e) => console.log(e));
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    dispatch(logoutAction());
    setVisibleRight(false);
    navigate('/', { replace: true });
  };

  return (
    <div>
      <Sidebar
        visible={visibleRight}
        position="right"
        onHide={() => setVisibleRight(false)}
        className={styles.sideBarDiv}
      >
        <div className={styles.myPage}>
          {user.id !== 0 && localStorage.getItem('accessToken') ? (
            <div className={styles.top}>
              <Link
                to={'/members'}
                className={styles.profile}
                onClick={() => setVisibleRight(false)}
              >
                <img src={getIcon(user.iconNumber)} alt="프로필이미지" width="56px" height="56px" />
                <div className={styles.name}>
                  <p className={styles.nickname}>{user.nickname}</p>
                  <p>{user.email}</p>
                </div>
              </Link>
              <p onClick={logout} className={styles.logOut}>
                로그아웃
              </p>
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

          <div className={styles.hr}></div>
          <p className={styles.midTitle}>안 읽은 알림</p>
          <AlertMessage></AlertMessage>
          <div className={styles.hr}></div>
        </div>
        <div className={styles.sideNav}>
          <NavLink to={'/study'} onClick={() => setVisibleRight(false)} className={styles.navText}>
            공부
          </NavLink>
          <NavLink
            to={'/community'}
            onClick={() => setVisibleRight(false)}
            className={styles.navText}
          >
            커뮤니티
          </NavLink>
          <NavLink to={'/store'} onClick={() => setVisibleRight(false)} className={styles.navText}>
            스토어
          </NavLink>
        </div>
      </Sidebar>
      <i className={cx('pi pi-bars', styles.navToggle)} onClick={() => setVisibleRight(true)} />
    </div>
  );
};

export default SidebarList;
