import styled from 'styled-components';
import { useNavigate } from 'react-router-dom';
import { easeOut, motion } from 'framer-motion';
import theme from '../../styles/theme';
import Button from '../atoms/Button';

function KioskAccount() {
  const navigate = useNavigate();

  const slide = {
    hidden: {
      opacity: 1,
      x: 1000,
      y: -500,
      rotate: 90,
      transition: { easeOut, duration: 0.7 },
    },
    visible: {
      opacity: 1,
      x: 0,
      y: 0,
      rotate: 0,
      transition: { when: 'beforeChildren', delay: 0.7 },
    },
  };

  const Done = () => {
    navigate(`/kiosk`);
  };

  return (
    <SAb>
      <SDiv variants={slide} initial="visible" animate="hidden">
        <SBtn type="button">구매</SBtn>
        <div />
        <SBtn type="button">반려</SBtn>
      </SDiv>
      <Sd variants={slide} initial="hidden" animate="visible">
        <div>우리은행</div>
        <div>1002-153-396120</div>
        <div>입금처 : 손다르크</div>
        <Ssub>구매자명으로 정확한 금액을 입금해주세요.</Ssub>
        <Ssub>입금 후 1분 이내에 문이 열립니다.</Ssub>
        <Button type={4} onClick={() => Done()}>
          닫기
        </Button>
      </Sd>
    </SAb>
  );
}

const Ssub = styled.h1`
  ${theme.font.Subtitle};
  color: ${theme.color.action};
`;

const SBtn = styled.button`
  padding: 60px;
  ${theme.box}
`;
const Sd = styled(motion.div)`
  position: absolute;
  padding: 1.5em;
  display: flex;
  gap: 1em;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  ${theme.font.Splash}
  ${theme.box}
`;

const SDiv = styled(motion.div)`
  position: absolute;
  width: 70%;
  height: 200px;
  display: flex;
  gap: 2em;
  justify-content: center;
  align-items: center;
  ${theme.font.Splash}
`;

const SAb = styled.div`
  width: 100vw;
  height: 100vh;
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
`;

export default KioskAccount;
