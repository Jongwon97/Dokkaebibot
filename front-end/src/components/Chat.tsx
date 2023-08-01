import React from 'react';
import styles from '../styles/components/Chat.module.scss';

const Chat = () => {
  return (
    <div className={styles.chatDiv}>
      <div className={styles.header}>
        header
      </div>
      <div className={styles.content}>
        content
      </div>
      <div className={styles.footer}>
        footer
      </div>
    </div>
  );
};

export default Chat;
