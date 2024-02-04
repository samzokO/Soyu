import styled from 'styled-components';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import LocalHeader from '../molecules/LocalHeader';
import NotificationBox from '../molecules/NotificationBox';
import theme from '../../styles/theme';

function Notification() {
  return (
    <>
      <LocalHeader>
        <BackBtn />
        알림내역
        <div />
      </LocalHeader>
      <MainContainerWithoutNav>
        <SNotiList>
          <NotificationBox>
            <SIMG src="/icons/favicon-32x32.png" alt="알림 사진" />
            <SContent>내 물품이 팔렸어요.</SContent>
            <STime>1시간 전</STime>
          </NotificationBox>
          <NotificationBox>
            <SIMG src="/icons/favicon-32x32.png" alt="알림 사진" />
            <SContent>
              구매자가 입금 확인 요청을 보냈어요! 입금여부를 확인해 주세요.
            </SContent>
            <STime>1시간 전</STime>
          </NotificationBox>
          <NotificationBox>
            <SIMG src="/icons/favicon-32x32.png" alt="알림 사진" />
            <SContent>구매자가 소유박스에 도착했어요!</SContent>
            <STime>1시간 전</STime>
          </NotificationBox>
          <NotificationBox>
            <SIMG src="/icons/favicon-32x32.png" alt="알림 사진" />
            <SContent>누군가 내가 파는 물품에 좋아요를 눌렀어요!</SContent>
            <STime>1시간 전</STime>
          </NotificationBox>
        </SNotiList>
      </MainContainerWithoutNav>
    </>
  );
}

export default Notification;

const SNotiList = styled.div`
  margin-top: 54px;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const SIMG = styled.img`
  width: 40px;
  height: 40px;
  background-color: blue;
  border-radius: 7px;
`;

const SContent = styled.div`
  ${theme.font.Body1}
  width: 80%;
`;

const STime = styled.div`
  ${theme.font.Body2}
  white-space: nowrap;
`;
