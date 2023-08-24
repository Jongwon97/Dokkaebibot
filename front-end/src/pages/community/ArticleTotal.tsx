import React from 'react';
import styles from '../../styles/pages/community/ArticleTotal.module.scss';
import CommunityHeader from './comp/CommunityHeader';
import ArticleList from './comp/ArticleList';
import MyArticle from './comp/MyArticle';

function CommunityArticle() {

    return (
        <div className={styles.div}>
            <CommunityHeader title='자유게시판 🎠' tab='community' />
            <div className={styles.postInner}>
                <div className={styles.profile}>
                    <MyArticle />
                </div>
                <div className={styles.postList}>
                    <ArticleList />
                </div>
            </div>
        </div>
    );
}

export default CommunityArticle;