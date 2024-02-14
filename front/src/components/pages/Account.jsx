import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import { motion } from 'framer-motion';
import useAccount from '../../hooks/useAccount';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import Button from '../atoms/Button';
import LocalHeader from '../molecules/LocalHeader';
import AccountMotion from '../../assets/icons/AccountMotion';
import TextField from '../molecules/TextField';

function Account() {
  const [data, getHandler, patchHandler, deleteHandler] = useAccount();
  const [inputData, setInputData] = useState({
    bankName: '',
    bankAccount: '',
  });
  useEffect(() => {
    getHandler();
  }, []);

  const HandleChange = (e) => {
    const { name, value } = e.target;
    setInputData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  return (
    <>
      <LocalHeader>
        <BackBtn />
        계좌 관리
        <div />
      </LocalHeader>
      <Container>
        <AccountMotion />
        {(data && (
          <FlexBox>
            <div>{data.bankName}</div>
            <div>{data.accountNumber}</div>
          </FlexBox>
        )) || (
          <FlexBox>
            <div>등록된 계좌가 없어요!</div>
            <TextField
              title="은행명"
              type="text"
              value={inputData.bankName}
              name="bankName"
              placeholder="은행명을 입력해주세요"
              onChange={HandleChange}
            />
            <TextField
              title="계좌번호"
              type="text"
              value={inputData.bankAccount}
              name="bankAccount"
              placeholder="계좌번호를 - 없이 숫자만 입력해주세요"
              onChange={HandleChange}
            />
          </FlexBox>
        )}
        <FlexBox>
          <MotionBox>
            <Button
              type={data ? '2' : '1'}
              onClick={() =>
                patchHandler(inputData.bankName, inputData.bankAccount)
              }
            >
              계좌 {data ? '수정' : '등록'}
            </Button>
          </MotionBox>
          <MotionBox>
            <Button type="4" onClick={deleteHandler}>
              계좌 삭제
            </Button>
          </MotionBox>
        </FlexBox>
      </Container>
    </>
  );
}

const MotionBox = styled(motion.div)`
  width: 100%;
`;

const FlexBox = styled.section`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const Container = styled(MainContainerWithoutNav)`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 100%;
  align-items: center;
`;

export default Account;
