import styled from 'styled-components';
import { useRef, useEffect, useState } from 'react';
import theme from '../../styles/theme';
import Button from '../atoms/Button';
import useManageTab from '../../hooks/useManageTab';
import {
  kioskSell,
  kioskBuy,
  kioskMakePurchase,
  kioskWithdraw,
} from '../../api/apis';

function Kiosk() {
  const [state, SetState] = useManageTab('sell');
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

  useEffect(() => {
    // 모든 inputValues가 채워졌을 때 버튼 활성화
    const isAllValuesFilled = inputValues.every((value) => value.length === 1);
    setIsButtonEnabled(isAllValuesFilled);
  }, [inputValues]);

  const handleInputChange = (index, event) => {
    const maxLength = 1;
    const value = event.target.value.slice(0, maxLength);

    const newInputValues = [...inputValues];
    newInputValues[index] = value;
    setInputValues(newInputValues);

    if (value.length === maxLength) {
      if (index < inputRefs.length - 1) {
        inputRefs[index + 1].current.focus();
      }
    }
  };

  const clearValues = () => {
    setInputValues(['', '', '', '', '', '']);
    inputRefs[0].current.focus();
  };

  const combineValues = () => {
    const combinedValue = inputRefs.map((ref) => ref.current.value).join('');
    clearValues();
    if (state === 'sell') {
      const a = kioskSell(combinedValue);
    } else if (state === 'withdrawal') {
      console.log('회수다');
      kioskWithdraw(combinedValue).then((response) => {
        const result = response.data.data;
        console.log(result);
      });
    }
  };

  return (
    <SContainer>
      <form action="">
        <SNumberContainer>
          <SFlexWrap>
            <SSellButton
              type="button"
              onClick={() => SetState('sell')}
              current={state}
            >
              판매
            </SSellButton>
            <SBuyButton
              type="button"
              onClick={() => SetState('buy')}
              current={state}
            >
              구매
            </SBuyButton>
            <SWithdrawalButton
              type="button"
              onClick={() => SetState('withdrawal')}
              current={state}
            >
              회수
            </SWithdrawalButton>
          </SFlexWrap>
          <STitle>확인번호 입력</STitle>
          <SFlexWrap>
            {inputRefs.map((ref, index) => (
              <SInput
                /* eslint-disable-next-line react/no-array-index-key */
                key={index}
                type="text"
                ref={ref}
                pattern="[0-9]*"
                maxLength="1"
                value={inputValues[index]}
                onClick={clearValues}
                onChange={(event) => handleInputChange(index, event)}
              />
            ))}
          </SFlexWrap>
          <Button type="0" Handler={combineValues} disabled={!isButtonEnabled}>
            입력
          </Button>
          <SBody1>물건의 확인번호를 입력해주세요.</SBody1>
        </SNumberContainer>
      </form>
    </SContainer>
  );
}

const SButton = styled.button`
  transition: 0.2s;
  padding: 10px;
  ${theme.font.Headline}
`;

const SSellButton = styled(SButton)`
  color: ${(props) =>
    props.current === 'sell'
      ? `${theme.color.action}`
      : `${theme.color.grayScale300}`};
  transform: ${(props) => (props.current === 'sell' ? 'scale(1.1)' : '')};
`;
const SBuyButton = styled(SButton)`
  color: ${(props) =>
    props.current === 'buy'
      ? `${theme.color.action}`
      : `${theme.color.grayScale300}`};
  transform: ${(props) => (props.current === 'buy' ? 'scale(1.1)' : '')};
`;
const SWithdrawalButton = styled(SButton)`
  color: ${(props) =>
    props.current === 'withdrawal'
      ? `${theme.color.action}`
      : `${theme.color.grayScale300}`};
  transform: ${(props) => (props.current === 'withdrawal' ? 'scale(1.1)' : '')};
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
  justify-content: center;
  align-items: center;
`;

const SNumberContainer = styled(SContainer)`
  width: 100%;
  max-width: 300px;
  min-width: 250px;
  height: 50vh;
  min-height: 300px;
  flex-direction: column;
  gap: 2em;
`;

export default Kiosk;
