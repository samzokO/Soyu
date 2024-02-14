import { styled } from 'styled-components';
import { motion, useAnimation } from 'framer-motion';
import { useState } from 'react';
import useMyPage from '../../hooks/useMypage';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import theme from '../../styles/theme';
import Button from '../atoms/Button';
import useChangeNickname from '../../hooks/useChangeNickname';

function insertProfile() {
  const data = useMyPage();
  console.log(data);
  const [nick, setNick] = useState();
  const [nickName, changeNickname] = useChangeNickname();
  const controls = useAnimation();
  const [isBouncing, setBouncing] = useState(false);
  const slide = {
    hidden: { opacity: 1, x: 10000, y: 0 },
    visible: { opacity: 1, x: 0, y: 0 },
  };

  const handleButtonClick = () => {
    setBouncing(true);
    controls.start({
      scale: [1, 1.1, 0.9, 1.1, 1],
      transition: { duration: 0.5 },
      onComplete: () => setBouncing(false),
    });
  };

  const handleChange = (e) => {
    setNick(e.target.value);
    console.log(nick);
  };

  return (
    <SContainer>
      <LocalHeader>
        <BackBtn />
        프로필 이미지
        <div />
      </LocalHeader>
      <SWelcome
        variants={slide}
        initial="hidden"
        whileTap={{ scale: 0.9 }}
        animate={isBouncing ? controls : 'visible'}
        onClick={handleButtonClick}
      >
        이미지 넣자
        <Button
          type={0}
          onClick={() => {
            changeNickname(nick);
          }}
        >
          변경
        </Button>
      </SWelcome>
    </SContainer>
  );
}

const SWelcome = styled(motion.div)`
  min-width: 300px;
  width: 50%;
  border-radius: 7px;
  display: flex;
  flex-direction: column;
  padding: 2em;
  ${theme.box}
`;

const SContainer = styled.main`
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  overflow: hidden;
`;

export default insertProfile;
