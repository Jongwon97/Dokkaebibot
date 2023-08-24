import React from 'react';
import styles from '../../styles/pages/community/ArticleCreate.module.scss';
import ArticleEditor from './comp/ArticleEditor';
import CommunityHeader from './comp/CommunityHeader';
import { useLocation } from 'react-router-dom';
import { Article } from '../../redux/reducers/articleReducer';

function ArticleCreate() {
    const { state }:{state:Article} = useLocation()
    return (
        <div className={styles.article}>
            <CommunityHeader title="자유게시판" tab='community' />
            <ArticleEditor state={state}/>
        </div>
    );
}

export default ArticleCreate;