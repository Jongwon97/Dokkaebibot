import React from 'react';
import styles from '../styles/components/Chips.module.scss';

const Chips = ({ text }: { text: string }) => {

  return (
    <>
      <div className={styles.outerDiv}>
        <p className={styles.innerText}>{text}</p>
      </div>
    </>
  );
};

export default Chips;
