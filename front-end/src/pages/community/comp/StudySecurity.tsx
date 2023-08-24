import React, { useState } from 'react';
import styles from '../../../styles/pages/community/comp/StudySecurity.module.scss';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/store';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';

function StudySecurity() {
  const studyroom = useSelector((state: RootState) => state.persistedReducer.studyroomReducer);

  // 비밀번호 수정 과정
  const [editPassword, isEditPassword] = useState<boolean>(false);
  // 비밀번호 이렇게 적으면 안 될 것 같기도 하고...
  const [password, setPassword] = useState<string>('');
  const updatePWHandler = () => {
    // 비밀번호 수정
    isEditPassword(!editPassword);
  };

  return (
    <div className={styles.StudySecurity}>
      <p className={styles.midTitle}>스터디룸 잠금</p>
      <p className={styles.lock}>{studyroom.lockStatus === 1 ? '비공개' : '공개'}</p>
      <p className={styles.midTitle}>비밀번호 변경</p>
      <div className={styles.password}>
        {editPassword === true && (
            <InputText
            value={password}
            type="password"
            className={styles.inputText}
            onChange={(e) => setPassword(e.target.value || '')}
            />
        )}
        <Button
            id="password"
            label={editPassword === false ? '변경' : '완료'}
            onClick={updatePWHandler}
            className={styles.button}
        />
      </div>
    </div>
  );
}

export default StudySecurity;
