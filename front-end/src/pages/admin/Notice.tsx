import React, { useEffect, useState } from 'react';
import styles from '../../styles/pages/admin/Notice.module.scss';
import { Link, useNavigate } from 'react-router-dom';
import { Button } from 'primereact/button';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import { UseSelector } from 'react-redux/es/hooks/useSelector';
import { getNoticeList } from '../../client/admin';

interface Notice {
  id: number;
  title: string;
  content: string;
  createdAt: string;
}

function Notice() {
  const navigate = useNavigate();
  const [noticeList, setNoticeList] = useState<Notice[]>([])
  const isAdmin = useSelector((state:RootState) => state.persistedReducer.memberReducer.isAdmin);


  useEffect(() => {
    const loadNoticeList =async () => {
      await getNoticeList()
        .then((response) => {
          setNoticeList(response.data)
        })
        .catch(e=>console.log(e))
    }
    loadNoticeList()
  }, [])

  return (
    <div className={styles.noticeDiv}>
      <div className={styles.notice}>공지사항</div>
      {isAdmin && (
        <Button
          label="공지사항 작성"
          className={styles.button}
          onClick={() => navigate('/notice/create')}
        />
      )}
      <div className={styles.noticeList}>
        <div className={styles.noticeBox}>
          {noticeList.map((notice) => (
            <div key={notice.id}>
              <Link to={`/notice/${notice.id}`}>
                <div className={styles.list}>
                  <p className={styles.title}>{notice.title}</p>
                  <p>
                    {notice.content.length > 166
                      ? notice.content.slice(0, 166) + '…'
                      : notice.content}
                  </p>
                  <div className={styles.under}>
                    <div>운영자 · {notice.createdAt}</div>
                  </div>
                </div>
              </Link>
              <div className={styles.hr}></div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default Notice;
