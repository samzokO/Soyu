import styled from 'styled-components';
import { useNavigate, useParams } from 'react-router-dom';
import { useEffect, useRef } from 'react';
import { easeOut, motion } from 'framer-motion';
import theme from '../../styles/theme';
import Button from '../atoms/Button';
import Keypad from '../molecules/Keypad';
import KeypadButton from '../atoms/KeypadButton';

function OpenForSale() {
  const navigate = useNavigate();
  const { lockerNumber } = useParams();
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
      transition: { easeOut, duration: 0.9 },
    },
    visible: {
      opacity: 1,
      x: 0,
      y: 0,
      rotate: 0,
      transition: { when: 'beforeChildren', delay: 0.7 },
    },
  };

  useEffect(() => {
    setTimeout(() => {
      navigate(-1);
    }, 20000);
  }, []);

  return (
    <SAb>
      <SContainer
        variants={slide}
        initial="visible"
        animate="hidden"
        end="visible"
      >
        <form action="">
          <SNumberContainer>
            <SFlexWrap>
              {buttons.map((button) => (
                <SButton>{button.label}</SButton>
              ))}
            </SFlexWrap>
            <STitle>확인번호 입력</STitle>
            <SFlexWrap>
              {inputRefs.map(() => (
                <SInput />
              ))}
            </SFlexWrap>
            <Button type="0">입력</Button>
            <SBody1>물건의 확인번호를 입력해주세요.</SBody1>
          </SNumberContainer>
        </form>
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
      <SAbsolute
        variants={slide}
        initial="hidden"
        animate="visible"
        end="hidden"
      >
        <div>{lockerNumber}번 보관함이 20초간 열려요!</div>
      </SAbsolute>
    </SAb>
  );
}

const SAb = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const SAbsolute = styled(motion.div)`
  position: absolute;
  width: 70%;
  height: 200px;
  display: flex;
  justify-content: center;
  align-items: center;
  ${theme.font.Splash}
  ${theme.box}
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

export default OpenForSale;
