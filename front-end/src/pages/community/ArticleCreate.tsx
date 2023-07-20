import React from 'react';
import styles from '../../styles/pages/community/ArticleCreate.module.scss';
import ArticleEditor from './comp/ArticleEditor';
import CommunityHeader from './comp/CommunityHeader';

function ArticleCreate() {

    return (
        <div className={styles.article}>
            <CommunityHeader title="자유게시판" />
            <ArticleEditor />
        </div>
    );
}

export default ArticleCreate;