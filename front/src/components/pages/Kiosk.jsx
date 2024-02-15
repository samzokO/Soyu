import styled from 'styled-components';
import { useRef, useEffect, useState } from 'react';
import theme from '../../styles/theme';
import Button from '../atoms/Button';
import useManageTab from '../../hooks/useManageTab';
import Keypad from '../molecules/Keypad';
import KeypadButton from '../atoms/KeypadButton';
import useKiosk from '../../hooks/useKiosk';
import { showErrorToast } from '../../utils/toastUtil';

function Kiosk() {
  const [state, SetState] = useManageTab('sell');
  const [data, sell, withdrawal, purchase] = useKiosk();
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
    // 첫번째 inputValues가 채워졌을 때 버튼 활성화
    const isAllValuesFilled = inputValues[0];
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
      sell(combinedValue)
        .then((res) => {
          console.log(res);
        })
        .catch(() => {
          showErrorToast('잘못된 코드입니다.');
        });
    } else if (state === 'withdrawal') {
      withdrawal(combinedValue).then((res) => {
        console.log(res);
      });
    } else if (state === 'buy') {
      purchase(combinedValue).then((res) => {
        console.log(res);
      });
    } else {
      showErrorToast('하고싶은 행동을 먼저 선택해주세요');
    }
  };

  const buttons = [
    { type: 'sell', label: '판매' },
    { type: 'buy', label: '구매' },
    { type: 'withdrawal', label: '회수' },
  ];

  const handleButtonClick = (buttonType) => {
    SetState(buttonType);
    inputRefs[0].current.focus();
  };

  const handleInput = (e) => {
    for (let i = 0; i < inputValues.length; i += 1) {
      const item = inputValues[i];
      if (item === '') {
        const newInputValues = [...inputValues];
        newInputValues[i] = e.target.innerText;
        inputRefs[i].current.focus();
        setInputValues(newInputValues);
        break;
      }
    }
  };

  const handleDelete = () => {
    const newInputValues = [...inputValues];
    for (let i = 0; i < inputValues.length; i += 1) {
      const item = inputValues[i];
      if (i === 0 && item === '') {
        break;
      }
      if (item === '') {
        newInputValues[i - 1] = '';
        inputRefs[i - 1].current.focus();
        setInputValues(newInputValues);
        break;
      } else if (i === 5) {
        newInputValues[i] = '';
        inputRefs[i].current.focus();
        setInputValues(newInputValues);
        break;
      }
    }
  };

  return (
    <SContainer>
      <form action="">
        <SNumberContainer>
          <SFlexWrap>
            {buttons.map((button) => (
              <SButton
                key={button.type}
                type="button"
                onClick={() => handleButtonClick(button.type)}
                curr={state === button.type ? 'true' : 'false'}
              >
                {button.label}
              </SButton>
            ))}
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
          <Button type="0" onClick={combineValues} disabled={!isButtonEnabled}>
            입력
          </Button>
          <SBody1>물건의 확인번호를 입력해주세요.</SBody1>
        </SNumberContainer>
      </form>
      <Keypad>
        <KeypadButton onClick={handleInput}>1</KeypadButton>
        <KeypadButton onClick={handleInput}>2</KeypadButton>
        <KeypadButton onClick={handleInput}>3</KeypadButton>
        <KeypadButton onClick={handleInput}>4</KeypadButton>
        <KeypadButton onClick={handleInput}>5</KeypadButton>
        <KeypadButton onClick={handleInput}>6</KeypadButton>
        <KeypadButton onClick={handleInput}>7</KeypadButton>
        <KeypadButton onClick={handleInput}>8</KeypadButton>
        <KeypadButton onClick={handleInput}>9</KeypadButton>
        <KeypadButton onClick={handleDelete}>지우기</KeypadButton>
        <KeypadButton onClick={handleInput}>0</KeypadButton>
        <KeypadButton onClick={clearValues}>초기화</KeypadButton>
      </Keypad>
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
