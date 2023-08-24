import React, { useEffect, useState } from 'react';
import styles from '../../../styles/pages/community/comp/MyArticle.module.scss';
import { useSelector } from 'react-redux';
import { Button } from 'primereact/button';
import { Link } from 'react-router-dom';
import Modal from '../../../components/Modal';
import { RootState } from '../../../redux/store';
import needLoginImg from '../../../assets/need_login.png';
import { getPopular } from '../../../client/article';
import { Article } from '../../../redux/reducers/articleReducer';
import { getMyStudyRooms } from '../../../client/studyroom';
import { getIcon } from '../../../client/members';

const MyArticle = () => {
  const user = useSelector((state: RootState) => state.persistedReducer.memberReducer);
  const isStudyRoom = window.location.pathname === '/community/studyroom';
  const [articleList, setArticleList] = useState<Article[]>([]);
  const [myArticle, setMyArticle] = useState(0);

  const [myStudyroom, setMyStudyroom] = useState(0);

  useEffect(() => {
    if (isStudyRoom === true) {
      const fetchstudyroomList = async () => {
        const response = await getMyStudyRooms();
        // 반환값이 배열일 경우만 셋팅
        if (Array.isArray(response.data)) {
          setMyStudyroom(response.data.length);
        }
      };
      fetchstudyroomList();
    } else {
      getPopular(0, 20)
        .then((response) => {
          setArticleList(response.data);
        })
        .catch((e) => console.log('error: ', e));
    }
  }, []);

  useEffect(() => {
    // articleList가 변경될 때마다 myArticle을 업데이트
    setMyArticle(
      articleList.reduce((count, article) => {
        if (article.writerNickname === user.nickname) {
          return count + 1;
        }
        return count;
      }, 0),
    );
  }, [articleList, user.nickname]);

  return (
    <div className={styles.div}>
      {user.id !== 0 ? (
        <>
          <img src={getIcon(user.iconNumber)} alt="깨비콘" className={styles.icon} width="92px" height="92px" />
          <p className={styles.name}>{user.nickname}</p>
          {isStudyRoom ? (
            <>
              {/* <p>게시물 {user.article.count()}   |   댓글  {user.comment.count()}</p> */}
              <p className={styles.gray}>
                내 스터디룸 <span className={styles.green}>{myStudyroom}</span>
              </p>
              <Modal type="create" />
            </>
          ) : (
            <>
              {/* <p>게시물 {user.article.count()}   |   댓글  {user.comment.count()}</p> */}
              <p className={styles.gray}>
                게시물 <span className={styles.green}>{myArticle}</span>
              </p>
              <Link to={'/community/article/create'}>
                <Button label="새 게시물 작성" className={styles.button} />
              </Link>
            </>
          )}
        </>
      ) : (
        <div className={styles.login}>
          <img src={needLoginImg} alt="로그인 해주세요" width="128px" height="128px" />
          <p>
            <Link to={'/members/login'} className={styles.link}>
              로그인
            </Link>
            이 필요한 서비스입니다
          </p>
        </div>
      )}
    </div>
  );
};

export default MyArticle;
