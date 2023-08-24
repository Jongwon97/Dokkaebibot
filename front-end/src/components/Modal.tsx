import React, { useState } from 'react';
import styles from '../styles/components/Modal.module.scss';
import classNames from 'classnames/bind';
import { Button } from 'primereact/button';
import StudyroomCreate from '../pages/community/StudyroomCreate';
import Chat from './Chat';
import StudyroomChat from '../pages/community/comp/StudyroomChat';
import StudyroomSetting from '../pages/community/comp/StudyroomSetting';

const cx = classNames.bind(styles);

const Modal = ({ type }: { type: string }) => {
  const [isOpen, setIsOpen] = useState(false);

  const openModalHandler = () => {
    setIsOpen(!isOpen);
  };

  return (
    <div className={styles.modalDiv}>
      <div className={styles.modalContainer}>
        {type === 'create' ? (
          <Button
            label="새 스터디룸 만들기"
            className={styles.modalBtn}
            onClick={openModalHandler}
          />
        ) : type === 'studyChat' ? (
          <i className={cx(styles.studyChat, 'pi pi-comments')} onClick={openModalHandler} />
        ) : type === 'studySetting' ? (
          <i className={cx(styles.studySetting, 'pi pi-cog')} onClick={openModalHandler} />
        ) : (
          <i className={cx(styles.message, 'pi pi-send')} onClick={openModalHandler} />
        )}
        {isOpen ? (
          <div className={styles.modalBackdrop} onClick={openModalHandler}>
            <div className={styles.modalView} onClick={(e) => e.stopPropagation()}>
              <i className={cx(styles.exitBtn, 'pi pi-times')} onClick={openModalHandler} />
              {type === 'create' ? (
                <StudyroomCreate />
              ) : type === 'studyChat' ? (
                <StudyroomChat />
              ) : type === 'studySetting' ? (
                <StudyroomSetting />
              ) : (
                <Chat />
              )}
            </div>
          </div>
        ) : null}
      </div>
    </div>
  );
};

export default Modal;
