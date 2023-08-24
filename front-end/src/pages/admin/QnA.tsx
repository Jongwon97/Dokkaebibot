import React, { useEffect, useState } from 'react';
import { useForm, Controller, SubmitHandler } from 'react-hook-form';
import styles from '../../styles/pages/admin/QnA.module.scss';
import classNames from 'classnames/bind';
import { Dropdown, DropdownChangeEvent } from 'primereact/dropdown';
import { Editor, EditorTextChangeEvent } from 'primereact/editor';
import { Button } from 'primereact/button';
import { useSelector } from 'react-redux';
import { RootState } from '../../redux/store';
import { postQna, deleteQna, getQnaList } from '../../client/admin';


interface qna {
  id: string,
  email: string,
  code: string,
  title: string,
  content: string,
}

function QnA() {
	const cx = classNames.bind(styles)
  const isAdmin = useSelector((state:RootState) => state.persistedReducer.memberReducer.isAdmin);

  interface Question {
    name: string;
    code: string;
    title: string;
    content: string;
  }

  const [selectedQuestion, setSelectedQuestion] = useState<Question | null>(null);
  const questions: Pick<Question, 'name' | 'code'>[] = [
    { name: '계정/프로필', code: 'ACCOUNT' },
    { name: '커뮤니티', code: 'COMMUNITY' },
    { name: '기기/연결', code: 'LINK' },
    { name: '기타', code: 'ETC' },
  ];

  const { control, register, handleSubmit, reset } = useForm<Question>();

  const onSubmitHandler: SubmitHandler<Question> = (data) => {
    // 문의사항 등록 과정
    postQna({
      ...data,
      code: selectedQuestion?.code
    })
      .then((response) => {
        alert('문의사항 등록이 완료되었습니다.');
      })
      .catch(e=>console.log(e))
    reset();
  };

  const [qnaList, setQnaList] = useState<qna[]>([])
  const [pageStatus, setPageStatus] = useState<number>(0)
  useEffect(() => {
    getQnaList()
      .then((response) => {
        setQnaList(response?.data)
      })
  }, [pageStatus])
  
  const deleteClicked = (qnaId:string) => {
    deleteQna(qnaId)
      .then((response) => {
        alert("문의가 삭제되었습니다.")
        setPageStatus(pageStatus + 1)
      })
  }

  return (
    <div className={styles.qna}>
      <p className={styles.title}>문의하기</p>
      {isAdmin ? (
        <div className={styles.noticeList}>
          <div className={styles.noticeBox}>
            {qnaList && qnaList.map((qna) => (
              <div key={qna.id}>
                  <div className={styles.list}>
                    <p className={styles.qTitle}>{qna.title}
                      <i className={cx(styles.icon, 'pi pi-trash')} onClick={()=>deleteClicked(qna.id)}></i></p>
                    <p>
                      {qna.content}
                    </p>
                    <div className={styles.under}>
                      <div>{qna.email}</div>
                    </div>
                  </div>
                <div className={styles.hr}></div>
              </div>
            ))}
          </div>
        </div>
      ) : (
        <form onSubmit={handleSubmit(onSubmitHandler)} className={styles.form}>
          <div className={styles.flex}>
            <Dropdown
              value={selectedQuestion}
              onChange={(e: DropdownChangeEvent) => setSelectedQuestion(e.value)}
              options={questions}
              optionLabel="name"
              placeholder="유형을 선택해주세요"
              className={styles.drop}
            />
            <input
              {...register('title')}
              type="text"
              placeholder="문의 제목을 입력해주세요"
              className={styles.titleInput}
            />
          </div>
          <Controller
            name="content"
            control={control}
            rules={{ required: 'Content is required.' }}
            render={({ field: { name, value, onChange } }) => (
              <Editor
                name={name}
                value={value}
                showHeader={false}
                placeholder="문의 내용을 입력해주세요"
                onTextChange={(e: EditorTextChangeEvent) => onChange(e.textValue || '')}
                pt={{
                  content: { style: { height: '320px' } },
                  toolbar: { className: 'surface-ground' },
                }}
              />
            )}
          />
          <Button type="submit" label="작성 완료" className={styles.button} />
        </form>
      )}
    </div>
  );
}

export default QnA;
