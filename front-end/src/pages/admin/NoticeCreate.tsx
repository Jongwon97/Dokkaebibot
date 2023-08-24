import styles from '../../styles/pages/admin/NoticeCreate.module.scss';
import { useDispatch } from 'react-redux';
import { useForm, Controller, SubmitHandler } from 'react-hook-form';
import { Editor, EditorTextChangeEvent } from 'primereact/editor';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { useLocation, useNavigate } from 'react-router-dom';
import React from 'react';
import { postNotice, updateNotice } from '../../client/admin';


interface Notice {
  id: Number;
  title: string;
  content: string;
}

function NoticeCreate() {
  const { state }: {state:Notice} = useLocation()
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const renderHeader = () => {
    return (
      <span className="ql-formats">
        <button className="ql-bold" aria-label="Bold"></button>
        <button className="ql-italic" aria-label="Italic"></button>
        <button className="ql-underline" aria-label="Underline"></button>
      </span>
    );
  };
  const header = renderHeader();

  const { control, register, handleSubmit, reset } = useForm<Notice>({
    defaultValues: state
  });

  const onSubmitHandler: SubmitHandler<Notice> = (data) => {
    // 공지사항 등록 과정
    let promise
    if (state === null) {
      promise = postNotice(data)
    } else {
      promise = updateNotice({
        ...data,
        createdAt: 0
      })
    }
    promise
      .then((response) => {
        alert('공지 작성 완료')
        navigate('/notice/' + response.data,
        {replace:true})
      })
      .catch((e)=>console.log(e))
  };

  return (
    <div className={styles.editor}>
      <h1>공지사항</h1>
      <form onSubmit={handleSubmit(onSubmitHandler)} className={styles.form}>
        <InputText
          {...register('title')}
          id="title"
          placeholder="제목을 입력해주세요"
          className={styles.title}
        />
        <Controller
          name="content"
          control={control}
          rules={{ required: 'Content is required.' }}
          render={({ field: { name, value, onChange } }) => (
            <Editor
              name={name}
              value={value}
              headerTemplate={header}
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
    </div>
  );
}

export default NoticeCreate;
