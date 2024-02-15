import styled from 'styled-components';
import { useNavigate, useParams } from 'react-router-dom';
import { useRef } from 'react';
import { easeOut, motion } from 'framer-motion';
import theme from '../../styles/theme';
import Button from '../atoms/Button';
import { showErrorToast } from '../../utils/toastUtil';
import { makePurchase } from '../../api/apis';
import Keypad from '../molecules/Keypad';
import KeypadButton from '../atoms/KeypadButton';

function KioskPurchase() {
  const { lockerNumber } = useParams();
  const navigate = useNavigate();
  const inputRefs = [
    useRef(),
    useRef(),
    useRef(),
    useRef(),
    useRef(),
    useRef(),
  ];

  const buttons = [
    { type: 'sell', label: '판매' },
    { type: 'buy', label: '구매' },
    { type: 'withdrawal', label: '회수' },
  ];

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

  const Buy = () => {
    makePurchase(true, lockerNumber).then(() => {
      navigate(`/kiosk/account`);
    });
  };

  const NoBuy = () => {
    // eslint-disable-next-line
    makePurchase(false, lockerNumber).then(() => {
      showErrorToast('반려되었습니다.');
      navigate(`/kiosk`);
    });
  };

  return (
    <SAb>
      <SContainer variants={slide} initial="visible" animate="hidden">
        <SNumberContainer>
          <SFlexWrap>
            {buttons.map((button, index) => (
              <SButton key={index}>{button.label}</SButton>
            ))}
          </SFlexWrap>
          <STitle>확인번호 입력</STitle>
          <SFlexWrap>
            {inputRefs.map((ref, index) => (
              <SInput key={index} />
            ))}
          </SFlexWrap>
          <Button type="0">입력</Button>
          <SBody1>물건의 확인번호를 입력해주세요.</SBody1>
        </SNumberContainer>
        <Keypad>
          <KeypadButton>1</KeypadButton>
          <KeypadButton>2</KeypadButton>
          <KeypadButton>3</KeypadButton>
          <KeypadButton>4</KeypadButton>
          <KeypadButton>5</KeypadButton>
          <KeypadButton>6</KeypadButton>
          <KeypadButton>7</KeypadButton>
          <KeypadButton>8</KeypadButton>
          <KeypadButton>9</KeypadButton>
          <KeypadButton>지우기</KeypadButton>
          <KeypadButton>0</KeypadButton>
          <KeypadButton>초기화</KeypadButton>
        </Keypad>
      </SContainer>
      <SDiv variants={slide} initial="hidden" animate="visible">
        <SBtn type="button" onClick={() => Buy()}>
          구매
        </SBtn>
        <div />
        <SBtn type="button" onClick={() => NoBuy()}>
          반려
        </SBtn>
      </SDiv>
    </SAb>
  );
}

const SBtn = styled.button`
  padding: 60px;
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
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const SButton = styled.button`
  transition: 0.2s;
  padding: 10px;
  ${theme.font.Headline}
  color: ${(props) =>
    props.curr === 'true'
      ? `${theme.color.action}`
      : `${theme.color.grayScale300}`};
  transform: ${(props) => (props.curr === 'true' ? 'scale(1.1)' : '')};
`;

const SFlexWrap = styled.div`
  display: flex;
  gap: 1vw;
  transition: 0.2s;
`;

const SInput = styled.input`
  width: 50px;
  height: 50px;
  padding-left: 18px;
  ${theme.font.Headline}
`;

const SBody1 = styled.div`
  ${theme.font.Body1}
`;

const STitle = styled.div`
  ${theme.font.Headline}
`;

const SContainer = styled(motion.div)`
  display: flex;
  width: 100vw;
  height: 100vh;
  gap: 7vw;
  justify-content: center;
  align-items: center;
`;

const SNumberContainer = styled.div`
  display: flex;
  width: 100%;
  max-width: 300px;
  min-width: 250px;
  height: 50vh;
  min-height: 300px;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  gap: 2em;
`;

export default KioskPurchase;
