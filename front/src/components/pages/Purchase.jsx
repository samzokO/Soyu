import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { useRef, useState } from 'react';
import { easeIn, easeInOut, easeOut, motion } from 'framer-motion';
import theme from '../../styles/theme';
import Button from '../atoms/Button';
import { showErrorToast } from '../../utils/toastUtil';
import { makePurchase } from '../../api/apis';

function KioskPurchase() {
  const { itemId, lockerNumber } = useParams();
  const inputRefs = [
    useRef(),
    useRef(),
    useRef(),
    useRef(),
    useRef(),
    useRef(),
  ];
  const [inputValues, setInputValues] = useState(['', '', '', '', '', '']);
  const [isButtonEnabled, setIsButtonEnabled] = useState(false);

  const handleInputChange = (index, event) => {};

  const clearValues = () => {};

  const combineValues = () => {};

  const buttons = [
    { type: 'sell', label: '판매' },
    { type: 'buy', label: '구매' },
    { type: 'withdrawal', label: '회수' },
  ];

  const handleButtonClick = () => {};

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
    makePurchase(true, lockerNumber).then((res) => {
      console.log(res);
    });
  };

  const NoBuy = () => {
    makePurchase(false, lockerNumber).then((res) => {
      console.log(res);
    });
  };

  return (
    <SContainer>
      <form action="">
        <SNumberContainer variants={slide} initial="visible" animate="hidden">
          <SFlexWrap>
            {buttons.map((button) => (
              <SButton>{button.label}</SButton>
            ))}
          </SFlexWrap>
          <STitle>확인번호 입력</STitle>
          <SFlexWrap>
            {inputRefs.map((ref, index) => (
              <SInput />
            ))}
          </SFlexWrap>
          <Button type="0" onClick={combineValues} disabled={!isButtonEnabled}>
            입력
          </Button>
          <SBody1>물건의 확인번호를 입력해주세요.</SBody1>
        </SNumberContainer>
      </form>
      <motion.div variants={slide} initial="hidden" animate="visible">
        <button type="button" onClick={() => Buy()}>
          구매
        </button>
        <div />
        <button type="button" onClick={() => NoBuy()}>
          안사
        </button>
      </motion.div>
    </SContainer>
  );
}

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

const SContainer = styled.div`
  display: flex;
  width: 100vw;
  height: 100vh;
  gap: 7vw;
  justify-content: center;
  align-items: center;
`;

const SNumberContainer = styled(motion(SContainer))`
  width: 100%;
  max-width: 300px;
  min-width: 250px;
  height: 50vh;
  min-height: 300px;
  flex-direction: column;
  gap: 2em;
`;

export default KioskPurchase;
