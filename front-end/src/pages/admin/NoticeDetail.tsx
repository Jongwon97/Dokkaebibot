import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import classNames from 'classnames/bind';
import styles from '../../styles/pages/admin/NoticeDetail.module.scss'

import { getNoticeDetail, deleteNotice } from '../../client/admin';

function NoticeDetail() {

	const cx = classNames.bind(styles)
	const navigate = useNavigate()
	const { id } = useParams();
	const isAdmin = useSelector((state:RootState) => state.persistedReducer.memberReducer.isAdmin)
	interface Notice {
		id: string,
		title: string,
		content: string
	}
	const [notice, setNotice] = useState<Notice>()

	useEffect(() => {
		const loadNoticeDetail = async (id:string) => {
			await getNoticeDetail(id)
				.then((response) => {
					setNotice(response.data)
				})
				.catch((e)=>console.log(e))
		}
		if (id) {
			loadNoticeDetail(id)
		}

	}, [])

  const deleteClicked = () => {
		if (notice) {
			deleteNotice(notice.id)
				.then(() => {
					navigate('/notice');
				})
				.catch((e) => console.log(e));
		}
  };

  const updateClicked = () => {
    navigate('/notice/create', 
		{ state: notice });
  };

  const goBack = () => {
    navigate(-1);
  };



	return (
    <div className={styles.div}>
			<div className={styles.notice}>공지사항</div>
      <div className={styles.detailBox}>
        <i onClick={goBack} className={cx(styles.left, 'pi pi-angle-left')}></i>

        <div className={styles.detailInner}>
          <div className={styles.detail}>
            <p className={styles.title}>
              {notice?.title}
              {isAdmin && (
                <>
                  <i className={cx(styles.icon, 'pi pi-trash')} onClick={deleteClicked}></i>
                  <i className={cx(styles.icon, 'pi pi-pencil')} onClick={updateClicked}></i>
                </>
              )}
            </p>
            <div className={styles.hr}></div>
						<div>{notice?.content}</div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default NoticeDetail;