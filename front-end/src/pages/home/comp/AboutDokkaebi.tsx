import React from 'react';
import styles from '../../../styles/pages/home/comp/AboutDokkaebi.module.scss';
import about01 from '../../../assets/about01.png';
import logo from '../../../assets/logo.png';
import effect01 from '../../../assets/effect01.png';
import effect02 from '../../../assets/effect02.png';
import effect03 from '../../../assets/effect03.png';
import footer from '../../../assets/footer.png';
import card01 from '../../../assets/card01.png';
import card02 from '../../../assets/card02.png';
import card03 from '../../../assets/card03.png';
import card04 from '../../../assets/card04.png';
import { Link } from 'react-router-dom';

function AboutDokkaebi() {
  const kkaebiEffect = [
    {
      title: '집중 효율 향상',
      content:
        '언제 어디서든 도깨비 봇과 함께라면 \n 호손 효과에 따른 높은 집중력으로 \n 효율적인 공부를 할 수 있습니다.',
      image: effect01,
    },
    {
      title: '공부 습관 분석',
      content:
        '구부정한 자세, 집중 못 한 시간 등 \n 공부 자세와 순 공부 시간을 분석하여 \n 더 나은 공부 환경을 제공합니다.',
      image: effect02,
    },
    {
      title: '스마트한 환경',
      content:
        '도깨비 봇은 공부 환경을 분석하여 \n 온습도 등 공부에 좋은 환경을 \n 연결된 기기를 통해 제어해줍니다.',
      image: effect03,
    },
  ];
  return (
    <div className={styles.about}>
      <div className={styles.main}>
        <img src={logo} alt="logo" width="40px" height="40px" />
        <p className={styles.title}>도깨비봇</p>
      </div>
      <div className={styles.content}>
        <div className={styles.midTitle}>
          <p>
            우리는 고민했습니다.
            <br />
            언제 어디서나 <br />
            <span className={styles.green}>집중</span>하는 방법 없을까?
          </p>
          <p className={styles.subText}>
            공부하려고 책상에 앉아도 졸고, 핸드폰 하고 집중이 안되는 집. <br />
            누군가 나를 지켜봐준다면 집중이 잘 되지 않을까? 하는 마음에 <br /> 카페, 독서실에 가던
            날들은 <strong>이제 그만!</strong>
          </p>
        </div>
        <img src={about01} alt="폰깨비" className={styles.img1} />
      </div>
      <div className={styles.content02}>
        <p className={styles.title02}>
          <strong className={styles.green}>도깨비봇</strong>은 도와주고 있습니다.
        </p>
        <div className={styles.effect}>
          {kkaebiEffect.map((effect, index) => (
            <div className={styles.effectDiv} key={index}>
              <img src={effect.image} alt="effect01" width={100} height={100} />
              <p className={styles.subTitle}>{effect.title}</p>
              <p className={styles.subContent}>{effect.content}</p>
            </div>
          ))}
        </div>
      </div>
      <div className={styles.content03}>
        <div className={styles.midTitle}>
          <p>
            “깨비야, 공부시작”
            <br />
            공부 습관 기록하고 <br />
            분석받아보세요
          </p>
        </div>
        <div className={styles.cardDiv}>
          <div className={styles.cards}>
            <img src={card01} alt='card01' className={styles.card} />
            <img src={card02} alt='card02' className={styles.card} />
            <img src={card03} alt='card03' className={styles.card} />
            <img src={card04} alt='card04' className={styles.card} />
            <img src={card01} alt='card01' className={styles.card} />
            <img src={card02} alt='card02' className={styles.card} />
            <img src={card03} alt='card03' className={styles.card} />
            <img src={card04} alt='card04' className={styles.card} />
          </div>
        </div>
      </div>
      <div className={styles.footer}>
        <img src={footer} alt="footer" className={styles.footerImg} />
        <div className={styles.textDiv}>
          <p className={styles.fTitle}>건강한 공부 습관을 찾고 있나요?</p>
          <p>공부비서 깨비가 도와드릴게요!</p>
          <Link to={'/members/register'}>
            <input type='button' value='지금 시작하기' className={styles.button} />
          </Link>
        </div>
      </div>
    </div>
  );
}

export default AboutDokkaebi;
