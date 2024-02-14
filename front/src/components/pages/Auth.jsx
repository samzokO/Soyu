import { styled } from 'styled-components';
import { motion, useAnimation } from 'framer-motion';
import { useState } from 'react';
import TextField from '../molecules/TextField';
import useMyPage from '../../hooks/useMypage';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import theme from '../../styles/theme';
import Button from '../atoms/Button';
import useChangeNickname from '../../hooks/useChangeNickname';

function Auth() {
  const data = useMyPage();
  const [nick, setNick] = useState(data.nickName);
  const changeNickname = useChangeNickname();
  const controls = useAnimation();
  const [isBouncing, setBouncing] = useState(false);
  const slide = {
    hidden: { opacity: 1, x: 1000, y: -500, rotate: 90 },
    visible: { opacity: 1, x: 0, y: 0, rotate: 0 },
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
  };

  return (
    <SContainer>
      <LocalHeader>
        <BackBtn />
        닉네임변경
        <div />
      </LocalHeader>
      <SWelcome
        variants={slide}
        initial="hidden"
        whileTap={{ scale: 0.9 }}
        animate={isBouncing ? controls : 'visible'}
        onClick={handleButtonClick}
      >
        <TextField
          title="닉네임 변경"
          type="text"
          placeholder={data.nickName}
          id="nickName"
          name="nickName"
          defaultValue={data.nickName}
          value={nick}
          onChange={handleChange}
        />
        <Button
          type={0}
          onClick={() => {
            if (nick) {
              changeNickname(nick);
            }
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

export default Auth;
