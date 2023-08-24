import React, { useState } from 'react';
import styles from '../../../styles/pages/community/comp/StudyroomList.module.scss';
import StudyroomListItem from './StudyroomListItem';
import { NavLink } from 'react-router-dom';
import { InputText } from 'primereact/inputtext';
import classNames from 'classnames/bind';

function StudyroomList(props: any) {
  const cx = classNames.bind(styles);

  const [searchText, setSearchText] = useState(''); // 상태로 검색어를 관리
  const [title, setTitle] = useState('');

  const search = () => {
    // 검색 코드 구현
    setTitle(searchText);
  };

  return (
    <>
      {props.title && (
        <div className={styles.midTitle}>
          <p className={styles.title}>{props.title}</p>
          {props.title === '스터디룸' && (
            <NavLink to={'/community/studyroom'} className={styles.navText}>
              더보기
            </NavLink>
          )}
          {props.title === '전체 스터디룸' && (
            <>
              <span className={cx(styles.navText, 'p-input-icon-right')}>
                <i className={cx(styles.search, 'pi pi-search')} onClick={search} />
                <InputText
                  placeholder="제목으로 스터디룸 찾기"
                  autoComplete="on"
                  className={styles.inputSearch}
                  value={searchText}
                  onChange={(e) => setSearchText(e.target.value)}
                />
              </span>
            </>
          )}
        </div>
      )}
      {props.value === '1' ? (
        <div className={styles.myOutDiv}>
          <StudyroomListItem value={props.value} title={title} />
        </div>
      ) : (
        <div className={styles.outDiv}>
          <StudyroomListItem value={props.value} title={title} />
        </div>
      )}
    </>
  );
}

export default StudyroomList;
