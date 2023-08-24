import React from 'react';
import styles from '../../styles/pages/community/Community.module.scss';
import ArticleListShort from './comp/ArticleListShort';
import CommunityHeader from './comp/CommunityHeader';
import Rank from './comp/Rank';
import MyArticle from './comp/MyArticle';
import StudyroomList from './comp/StudyroomList';

function Community() {
  return (
    <div className={styles.community}>
      <CommunityHeader title='이번 주 깨비 Rank 👑' tab='community' />
      <Rank />
      <div className={styles.post}>
        <p className={styles.midTitle}>자유게시판</p>
        <div className={styles.postInner}>
          <div className={styles.profile}>
            <MyArticle />
          </div>
          <div className={styles.postList}>
            <ArticleListShort />
          </div>
        </div>
      </div>
      <div className={styles.study}>
        <StudyroomList title='스터디룸' value="3" />
      </div>
    </div>
  );
}

export default Community;