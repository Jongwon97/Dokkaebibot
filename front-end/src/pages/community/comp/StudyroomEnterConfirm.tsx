import React, { useState, useEffect } from 'react';
import styles from '../../../styles/pages/community/comp/StudyroomEnterConfirm.module.scss';
import classNames from 'classnames/bind';
import { useNavigate, useLocation } from 'react-router-dom';
import { Studyroom } from '../../../redux/reducers/studyroomReducer';
import { getStudyRoomDetail, updateStudyroomMember } from '../../../client/studyroom';
import { Button } from 'primereact/button';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/store';

const StudyroomEnterConfirm = () => {
  const cx = classNames.bind(styles);
  const navigate = useNavigate();
  const { state } = useLocation();

  const [curStudyRoom, setStudyRoom] = useState<Studyroom>();
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [isCorrect, setIsCorrect] = useState(false);
  const errorMessage = '비밀번호가 일치하지 않습니다.';

  const userId = useSelector((state: RootState) => state.persistedReducer.memberReducer.id);


  useEffect(() => {
    // studyroom detail 에서 연결된 경우
    // title === roomId 이기 때문에 title로 studyroom detail 불러오기 (axios)
    const roomId = Number(state); 
    localStorage.setItem('roomId', String(roomId));

    // StudyRoom Detail 정보 조회
    getStudyRoomDetail({ roomId })
      .then((response) => {
        setStudyRoom(response.data);
      })
      .catch((error) => {
        // Promise가 실패했을 때 실행될 코드
        console.error('Error fetching study room detail:', error);
      });
  }, [state]);

  const handleChange = (e: any) => {
    const enteredPassword = e.target.value;
    setPasswordConfirm(enteredPassword);

    if (curStudyRoom?.password === enteredPassword) {
      setIsCorrect(true);
    } else {
      setIsCorrect(false);
    }
  };

  const enterStudyroom = () => {
    // studyroom Member List 에 등록해주세요 (일단 만들었는데 client 확인 필요)
    const roomId = Number(state); 
    let promise = updateStudyroomMember({ roomId });
    promise
    .then((response) => {
      // 스터디룸 생성 성공시
      if (response.data.message === 'success') {
        alert("가입을 환영합니다!")
        navigate('/community/studyroom/' + curStudyRoom?.room_id, {
          state: curStudyRoom?.room_id,
        });
      } 
      else if(response.data.message === 'overcapacity'){
        alert("정원 초과!");
      }
      else {
       // 이미 가입했던 멤버일 경우
      navigate('/community/studyroom/' + curStudyRoom?.room_id, {
        state: curStudyRoom?.room_id,
      });
      }
    })
    .catch((e) => console.log(e));

  };

  const goBack = () => {
    navigate(-1);
  };

  return (
    <div className={styles.modalDiv}>
      <div className={styles.modalContainer}>
        <div className={styles.modalBackdrop} onClick={goBack}>
          <div className={styles.modalView} onClick={(e) => e.stopPropagation()}>
            <i className={cx(styles.exitBtn, 'pi pi-times')} onClick={goBack} />
            <div className={styles.top}>
              <h2>
                {curStudyRoom?.lockStatus === 1 && (
                  <i className={cx(styles.lockIcon, 'pi pi-lock')} />
                )}
                {curStudyRoom?.title}
              </h2>
              <span>
                {curStudyRoom?.curAttendance} / {curStudyRoom?.maxCapacity}
              </span>
            </div>
            <p>
              <strong>{curStudyRoom?.title}</strong>의 스터디원이 되시겠습니까?
            </p>

            {curStudyRoom?.lockStatus === 1 ? (
              <div className={styles.lockRoom}>
                <input
                  type="password"
                  value={passwordConfirm}
                  placeholder="비밀번호를 입력해주세요"
                  onChange={handleChange}
                  className={styles.password}
                />
                {passwordConfirm !== '' && isCorrect === false && (
                  <p className={styles.error}>{errorMessage}</p>
                )}
                <Button
                  label="스터디룸 입장하기"
                  className={styles.submitBtn}
                  onClick={() => enterStudyroom()}
                  disabled={!isCorrect}
                />
              </div>
            ) : (
              <div className={styles.btnDiv}>
                <input value="아니요" type="button" className={styles.rejectBtn} onClick={goBack} />
                <Button
                    label="스터디룸 입장하기"
                    className={styles.enterBtn}
                    onClick={() => enterStudyroom() }
                />
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudyroomEnterConfirm;
