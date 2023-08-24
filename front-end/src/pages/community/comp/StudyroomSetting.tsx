import React, { useState } from 'react';
import styles from '../../../styles/pages/community/comp/StudyroomSetting.module.scss';
import classNames from 'classnames/bind';
import StudyMemberSetting from './StudyMemberSetting';
import StudyExitSetting from './StudyExitSetting';
import StudySetting from './StudySetting';
import StudySecurity from './StudySecurity';

const StudyroomSetting = () => {
  const cx = classNames.bind(styles);
  const [currentTab, clickTab] = useState(0);

  const tabMenu = [
    { name: '스터디룸', content: <StudySetting /> },
    { name: '스터디원', content: <StudyMemberSetting /> },
    { name: '보안', content: <StudySecurity /> },
    { name: '나가기', content: <StudyExitSetting /> },
  ];

  const clickTabHandler = (now: number) => {
    clickTab(now);
  };

  return (
    <div className={styles.div}>
      <p className={styles.title}>
        <i className={cx(styles.icon, 'pi pi-cog')} />
        스터디룸 설정
      </p>
      <div className={styles.content}>
        <div className={styles.tabNav}>
          {tabMenu.map((menu, now) => (
            <div
              className={now === currentTab ? styles.now : styles.next}
              onClick={() => clickTabHandler(now)}
              key={menu.name}
            >
              {menu.name}
            </div>
          ))}
        </div>
        <div className={styles.hrCol} />
        <div className={styles.tabContent}>
          <div className={styles.content}>{tabMenu[currentTab].content}</div>
        </div>
      </div>
    </div>
  );
};

export default StudyroomSetting;
