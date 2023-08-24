import React, { useEffect, useState } from 'react';
import styles from '../../../styles/pages/community/comp/ArticleComment.module.scss';
import { useForm, SubmitHandler } from 'react-hook-form';
import { InputTextarea } from 'primereact/inputtextarea';
import { Button } from 'primereact/button';
import { Comment, setCommentList } from '../../../redux/reducers/commentReducer'
import { postComment, getFromArticle, deleteComment } from '../../../client/comment';
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from '../../../redux/store';
import classNames from 'classnames/bind';
import Loading from '../../../components/Loading';


function CommunityHeader() {
  const cx = classNames.bind(styles);
  const { register, handleSubmit } = useForm<Comment>();
  const [value, setComment] = useState<string>('');
  const [pageStatus, setPageStatus] = useState<number>(0)
  const articleDetail = useSelector((state:RootState) => state.persistedReducer.articleReducer)
  const articleId = articleDetail.id;
  const commentList = useSelector((state:RootState) => state.persistedReducer.commentReducer)
  const dispatch = useDispatch();

  const [isLoading, setIsLoading] = useState(false)
  const setLoadingFalse = () => {
    setIsLoading(false)
  }
  
  const user = useSelector((state: RootState) => state.persistedReducer.memberReducer)
  const memberId = user.id


  const onSubmitHandler: SubmitHandler<Comment> = (data: Comment) => {
    if (data.content !== "") {
      setIsLoading(true)
      postComment(articleId, data)
        .then((response)=>{
          getFromArticle(articleId)
            .then((response) => {
              dispatch(setCommentList(response.data))
              setComment('')
              setTimeout(setLoadingFalse, 500)
            })
        })
        .catch((e)=>console.log(e));
    }
  };

  useEffect(() => {
    if (articleId !== undefined) {
      getFromArticle(articleId)
        .then((response) => {
          dispatch(setCommentList(response.data))
        })
        .catch(e=>console.log(e))
    }
  }, [articleId, pageStatus, isLoading])

  const deleteClicked = (commentId:number) => {
    deleteComment(commentId)
      .then(() => {setPageStatus(pageStatus + 1)})
      .catch((e) => console.log(e));
  };


  return (
    <div className={styles.review}>
      <p className={styles.title}>댓글({commentList.length})</p>
      {isLoading && <Loading/>}
      <form onSubmit={handleSubmit(onSubmitHandler)} className={styles.form}>
        <InputTextarea
          autoResize
          {...register('content')}
          value={value}
          onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) =>
            setComment(e.target.value)
          }
          required={true}
          rows={3}
          className={styles.textArea}
          placeholder="댓글을 입력해주세요.
            (커뮤니티 댓글 규칙을 준수해주세요)"
        />
        <Button
          label="댓글 등록"
          type="submit"
          className={styles.inputButton}
        />
      </form>
      <div className={styles.hr}></div>
      <div className={styles.reviewList}>
        { commentList.map ((comment:Comment) => (
            <div key={comment.id} >
                <div className={styles.list}>
                    <p>{ comment.content }</p>
                    <p className={styles.name}>
                      { comment.writerNickname } · { comment.createdAt }
                      {(memberId === comment.writerId
                        || user.isAdmin) && (
                          <i className={cx(styles.icon, 'pi pi-trash')} 
                          onClick={()=>{deleteClicked(comment.id)}}></i>
                      )}
                    </p>
                </div>
                <div className={styles.hr}></div>
            </div>
        ))}
      </div>
    </div>
  );
}

export default CommunityHeader;
