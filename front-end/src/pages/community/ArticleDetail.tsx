import React, { useEffect, useRef, useState } from 'react';
import { useParams, useNavigate, Link } from 'react-router-dom';
import { getDetail, deleteArticle } from '../../client/article';
import classNames from 'classnames/bind';
import {
  setArticleData,
  toggleArticleLike,
  toggleArticleScrap,
} from '../../redux/reducers/articleReducer';
import { useDispatch, useSelector } from 'react-redux';
import { Button } from 'primereact/button';
import styles from '../../styles/pages/community/ArticleDetail.module.scss';
import CommunityHeader from './comp/CommunityHeader';
import ArticleComment from './comp/ArticleComment';
import { RootState } from '../../redux/store';
import { toggleLike, toggleScrap} from '../../client/article';
import dompurify from 'dompurify';
import Loading from '../../components/Loading';

function ArticleDetail() {
  const cx = classNames.bind(styles);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const { id } = useParams();
  const articleDetail = useSelector((state: RootState) => state.persistedReducer.articleReducer);
  const user = useSelector((state: RootState) => state.persistedReducer.memberReducer)
  const memberId = user.id
  const [isLoading, setIsLoading] = useState<boolean>(false)
  const setLoadingFalse = () => {
    setIsLoading(false)
  }

  useEffect(() => {
    if (id !== undefined) {
      getDetail(id)
        .then((response) => {
          dispatch(setArticleData(response.data));
        })
        .catch((e) => console.log('error: ', e));
    }
  }, [id, isLoading]);

  let content = articleDetail.content;

  const likeButtonClicked = () => {
    setIsLoading(true)
    dispatch(toggleArticleLike());
    if (id) {
      toggleLike(id)
        .then(() => {setTimeout(setLoadingFalse, 200)})
        .catch((e) => {console.log(e)})
    }
  };

  const scrapButtonClicked = () => {
    setIsLoading(true)
    dispatch(toggleArticleScrap());
    if (id) {
      toggleScrap(id)
        .then(() => {setTimeout(setLoadingFalse, 200)})
        .catch((e) => {console.log(e)})
    }
  };

  const deleteClicked = () => {
    deleteArticle(articleDetail.id)
      .then(() => {
        navigate('/community');
      })
      .catch((e) => console.log(e));
  };

  const updateClicked = () => {
    navigate('/community/article/create', { state: articleDetail });
  };

  const goBack = () => {
    navigate(-1);
  };

  return (
    <div className={styles.div}>
      <CommunityHeader title="자유게시판 🎠" tab="community" />
      <div className={styles.detailBox}>
        <i onClick={goBack} className={cx(styles.left, 'pi pi-angle-left')}></i>
        {isLoading && <Loading/>}
        <div className={styles.detailInner}>
          <div className={styles.detail}>
            <p className={styles.title}>
              {articleDetail.title}
              {(memberId === articleDetail.writerId
                || user.isAdmin) && (
                <>
                  <i className={cx(styles.icon, 'pi pi-trash')} onClick={deleteClicked}></i>
                  <i className={cx(styles.icon, 'pi pi-pencil')} onClick={updateClicked}></i>
                </>
              )}
            </p>
            <p>
              <Link to={`${articleDetail.writerId}`}>{articleDetail.writerNickname}</Link> ·{' '}
              {articleDetail.createdAt}
            </p>
            <div className={styles.hr}></div>
            {window ? (
              <div
                className={styles.content}
                dangerouslySetInnerHTML={{ __html: dompurify.sanitize(content) }}
              />
            ) : (
              <div />
            )}
            <div className={styles.button}>
              <Button
                className={cx(
                  articleDetail.liked
                    ? `${styles.liked} pi pi-thumbs-up-fill`
                    : `${styles.unliked} pi pi-thumbs-up`,
                )}
                onClick={likeButtonClicked}
              >
                <p className={styles.innerButton}>공감</p>
                {articleDetail.numLike}
              </Button>
              <Button
                className={cx(
                  articleDetail.scrapped
                    ? `${styles.scraped} pi pi-bookmark-fill`
                    : `${styles.unscraped} pi pi-bookmark`,
                )}
                onClick={scrapButtonClicked}
              >
                <p className={styles.innerButton}>저장</p>
                {articleDetail.numScrap}
              </Button>
            </div>
          </div>
          <div className={styles.review}>
            <ArticleComment />
          </div>
        Loading</div>
      </div>
    </div>
  );
}

export default ArticleDetail;
