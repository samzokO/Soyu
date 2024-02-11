import { useEffect } from 'react';
import { styled } from 'styled-components';
import useAccount from '../../hooks/useAccount';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import Button from '../atoms/Button';
import LocalHeader from '../molecules/LocalHeader';

function Account() {
  const [data, getHandler, patchHandler, deleteHandler] = useAccount();
  useEffect(() => {
    getHandler();
  }, []);
  return (
    <>
      <LocalHeader>
        <BackBtn />
        계좌 관리
        <div />
      </LocalHeader>
      <Container>
        {(data && (
          <div>
            <div>{data.bankName}</div>
            <div>{data.accountNumber}</div>
          </div>
        )) || (
          <div>
            <div>계좌 미등록</div>
            <div>등록해</div>
          </div>
        )}
        <div>
          <Button type={data ? '2' : '1'} onClick={patchHandler}>
            계좌 {data ? '수정' : '등록'}
          </Button>
          <Button type="4" onClick={deleteHandler}>
            계좌 삭제
          </Button>
        </div>
      </Container>
    </>
  );
}

const Container = styled(MainContainerWithoutNav)`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

export default Account;
