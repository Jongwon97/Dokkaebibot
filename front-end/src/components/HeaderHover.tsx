import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styles from '../styles/components/HeaderHover.module.scss';
import AlertMessage from './Alert'
import { useDispatch } from 'react-redux';
import { logoutAction } from '../redux/reducers/memberReducer';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import { memberLogout } from '../client/members';
import { getIcon } from '../client/members';

const HeaderHover = () => {

	const navigate = useNavigate()
	const dispatch = useDispatch()

	const logout = () => {
		memberLogout()
			.catch(e=>console.log(e))
		localStorage.removeItem("accessToken")
		localStorage.removeItem("refreshToken")
		dispatch(logoutAction());
		navigate("/", { replace: true })
	}

	const user = useSelector((state: RootState) => state.persistedReducer.memberReducer)
	return (
		<div className={styles.hover}>
			<p className={styles.title}>마이페이지</p>
			<div className={styles.top}>
				<Link to={"/members"} className={styles.profile}>
					<img src={getIcon(user.iconNumber)} alt='프로필이미지' width='48px' height='48px' />
					<div className={styles.name}>
						<p className={styles.nickname}>{ user.nickname }</p>
						<p>{ user.email }</p>
					</div>
				</Link>
				<p onClick={logout} className={styles.logOut}>로그아웃</p>
			</div>
			<div className={styles.hr}></div>
			<p className={styles.midTitle}>안 읽은 알림</p>
			<AlertMessage></AlertMessage>
		</div>
	)
}

export default HeaderHover;
