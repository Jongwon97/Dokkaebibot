import React, { useState } from 'react';
import styles from '../../../styles/pages/home/comp/ConnectBot.module.scss';
import { Link } from 'react-router-dom';
import { Button } from 'primereact/button';
import kkaebi from '../../../assets/connect.png';
import { InputText } from 'primereact/inputtext';
import { useForm, SubmitHandler } from 'react-hook-form';
import classNames from 'classnames/bind';
import { getMemberInfo } from '../../../client/members';
import { registDevice } from '../../../client/device';
import { setHaveDevice } from '../../../redux/reducers/memberReducer';
import { useDispatch } from 'react-redux';

function ConnectBot() {
  const cx = classNames.bind(styles);
  const dispatch = useDispatch();
  const [step, setStep] = useState(0);

  interface serialNumber {
    id: string;
  }
  const { register, handleSubmit, formState } = useForm<serialNumber>();

  const onSubmitHandler: SubmitHandler<serialNumber> = async (data) => {
    registDevice(data.id)
      .then(() => {
        getMemberInfo().then((res) => {
          dispatch(setHaveDevice(res.data));
          setStep(2);
        })
      })
      .catch((e) => {
        alert("이미 사용되고 있는 시리얼 넘버입니다.")
        console.log(e)
      })
  };

  const serialNumberRegex = new RegExp(/^SN[0-9]{6}$/)

  return (
    <div className={styles.beforeLogin}>
      <img src={kkaebi} alt="깨비사진" className={styles.kkaebi} />
      <div className={styles.right}>
        {step === 0 ? (
          <>
            <h1 className={styles.title}>
              나만의 공부비서 <br />
              <span>도깨비 봇</span> 등록하고 <br /> 시작해볼까요?
            </h1>
            <Button label="등록하기" className={styles.signup} onClick={() => setStep(1)} />
          </>
        ) : (
          <>
            <div className={styles.registerDiv}>
              <div className={styles.stepBar}>
                <div className={styles.line}></div>
                <div className={styles.stepCircles}>
                  <div className={cx(styles.circle, styles.didCircle)}></div>
                  <div className={cx(styles.circle, step === 2 && styles.didCircle)}></div>
                </div>
              </div>
              <h2>{step === 1 ? '도깨비봇 등록' : '등록이 완료되었습니다'}</h2>
              {step === 1 ? (
                <>
                  <form onSubmit={handleSubmit(onSubmitHandler)}>
                    <InputText
                      {...register('id', {required: true,
                        pattern: {
                          value: serialNumberRegex,
                          message: '시리얼 넘버 형식이 올바르지 않습니다. 시리얼 넘버는 SN으로 시작하는 6자리 숫자입니다.'
                        }
                      })}
                      placeholder="시리얼 넘버를 입력해주세요"
                      id="id"
                      className={styles.inputBox}
                    />
                    <div className={styles.errorText}>{(formState.errors.id?.message)?.slice(0, 21)}</div>
                    <div className={styles.errorTextBottom}>{(formState.errors.id?.message)?.slice(21)}</div>
                    <Button label="다음" type="submit" className={styles.inputButton} />
                  </form>
                  <p className={styles.info}>
                    <i className={cx(styles.icon, 'pi pi-question-circle')} />
                    시리얼 넘버가 안보여요
                  </p>
                </>
              ) : (
                <div>
                  <p className={styles.text}>
                    공부 비서 깨비 등록이 완료되었습니다. <br /> <strong>“깨비야”</strong> 하고
                    불러보세요
                  </p>
                  <Link to={'/'}>
                    <Button label="완료" className={styles.inputButton} />
                  </Link>
                </div>
              )}
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default ConnectBot;
