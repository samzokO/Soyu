import styled from 'styled-components';
import BottomNav from '../molecules/BottomNav';
import ChatComponent from '../molecules/ChatComponent';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import useLoadRooms from '../../hooks/useLoadRooms';

function ChatList() {
  const rooms = useLoadRooms();

  console.log(rooms);

  return (
    <>
      <LocalHeader>
        <BackBtn />
        채팅
        <div />
      </LocalHeader>
      <MainContainerWithNav>
        <SList>
          <ChatComponent />
          <ChatComponent />
          <ChatComponent />
        </SList>
      </MainContainerWithNav>

      <BottomNav />
    </>
  );
}

const SList = styled.ul`
  padding: 10px 0px;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

export default ChatList;
