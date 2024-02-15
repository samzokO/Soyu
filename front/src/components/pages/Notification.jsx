import styled from 'styled-components';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import LocalHeader from '../molecules/LocalHeader';
import NotificationBox from '../molecules/NotificationBox';
import theme from '../../styles/theme';
import useNotification from '../../hooks/useNotificationList';

function Notification() {
  const data = useNotification();

  return (
    <>
      <LocalHeader>
        <BackBtn />
        알림내역
        <div />
      </LocalHeader>
      <MainContainerWithoutNav>
        <SNotiList>
          {data?.map((notice) => (
            <NotificationBox key={notice.noticeId} notice={notice} />
          ))}
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
