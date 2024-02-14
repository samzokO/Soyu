import styled from 'styled-components';
import { motion } from 'framer-motion';
import Logo from '../../assets/icons/Logo.svg';
import KakaoLogin from '../atoms/KakaoLogin';
import NaverLogin from '../atoms/NaverLogin';
import GoogleLogin from '../atoms/GoogleLogin';
import theme from '../../styles/theme';

function Login() {
  const list = {
    hidden: {
      opacity: 0,
    },
    visible: {
      opacity: 1,
      transition: {
        when: 'beforeChildren',
        staggerChildren: 0.3,
      },
    },
  };

  const item = {
    hidden: { opacity: 0, y: 50 },
    visible: { opacity: 1, y: 0 },
  };

  return (
    <SWrap>
      <motion.img
        initial={{ opacity: 0, scale: 0.5 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{
          duration: 1,
          delay: 0.3,
          ease: [0, 0.71, 0.2, 1.01],
        }}
        src={Logo}
        alt="#"
      />
      <STitle
        initial={{ opacity: 0, scale: 0.5 }}
        animate={{ opacity: 1, scale: 1 }}
        transition={{
          duration: 1,
          delay: 0.3,
          ease: [0, 0.71, 0.2, 1.01],
        }}
      >
        소유
      </STitle>
      <SNav variants={list} initial="hidden" animate="visible">
        <SMdiv variants={item}>
          <NaverLogin />
        </SMdiv>
        <SMdiv variants={item}>
          <KakaoLogin />
        </SMdiv>
        <SMdiv variants={item}>
          <GoogleLogin />
        </SMdiv>
        <SMdiv variants={item}>
          <div>로그인 안할래요</div>
        </SMdiv>
      </SNav>
    </SWrap>
  );
}

export default Login;
const SMdiv = styled(motion.div)``;

const STitle = styled(motion.h1)`
  margin: 20px 0;
  ${theme.font.Splash}
`;

const SWrap = styled(motion.div)`
  width: 100vw;
  height: 100vh;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const SNav = styled(motion.nav)`
  width: 200px;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;
