import React, { useState } from 'react';
import styles from '../../../styles/pages/members/comp/RobotSetting.module.scss';
import { useSelector } from 'react-redux';
import { RootState } from '../../../redux/store';
import classNames from 'classnames/bind';
import { Link } from 'react-router-dom';
import { Button } from 'primereact/button';
import iot from '../../../assets/iot.png';
import humi from '../../../assets/humi.png';
import purifier from '../../../assets/purifier.png';
import speaker from '../../../assets/speaker.png';
import aircon from '../../../assets/aircon.png';

function RobotSetting() {
  const cx = classNames.bind(styles);
  const user = useSelector((state: RootState) => state.persistedReducer.memberReducer);

  const deleteSerialNum = () => {
    // 시리얼 넘버 삭제
    setIsEdit(false);
  };

  const [isEdit, setIsEdit] = useState<Boolean>(false);

  return (
    <div className={styles.robotDiv}>
      <div className={styles.robotList}>
        <div className={styles.top}>
          <p>기본 정보</p>
          <p className={styles.edit} onClick={() => setIsEdit(!isEdit)}>
            편집
          </p>
        </div>
        <div className={styles.bottom}>
          {user.haveDevice ? (
            <img src={iot} alt="깨비" className={styles.iot} width={'40%'} />
          ) : (
            <p className={styles.iot}>도깨비봇 연결이 필요합니다</p>
          )}

          <div className={styles.serial}>
            <p>시리얼 넘버</p>
            <p className={styles.linked}>
              <i className="pi pi-link" style={{ margin: '8px' }}></i>
              {user.haveDevice ? '연결되어있음' : <Link to={'/connect'}>연결하기</Link>}
            </p>
          </div>
          {isEdit && (
            <Button
              id="serial"
              label="연결해제"
              onClick={deleteSerialNum}
              className={styles.button}
            />
          )}
        </div>
      </div>

      <div className={styles.linkDiv}>
        <div className={styles.top}>
          <p>스마트 연결</p>
        </div>
        <div className={styles.electron}>
          <div className={styles.item}>
            <img src={humi} alt="가습기" className={styles.iot} width={'60%'} />
            <Button id="humi" label={user.haveDevice === true ? '연결됨' : '연결하기'} className={cx(styles.button, user.haveDevice === true && styles.linkedButton)} />
          </div>
          <div className={styles.item}>
            <img src={aircon} alt="에어컨" className={styles.iot} width={'58%'} />
            <Button id="aircon" label="연결하기" className={styles.button} />
          </div>
          <div className={styles.item}>
            <img src={purifier} alt="공기청정기" className={styles.iot} width={'60%'} />
            <Button id="purifier" label="연결하기" className={styles.button} />
          </div>
          <div className={styles.item}>
            <img src={speaker} alt="스피커" className={styles.iot} width={'60%'} />
            <Button id="speaker" label="연결하기" className={styles.button} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default RobotSetting;
