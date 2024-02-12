import styled from 'styled-components';
import BottomNav from '../molecules/BottomNav';
import ChatComponent from '../molecules/ChatComponent';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import useLoadRooms from '../../hooks/useLoadRooms';

function ChatList() {
  const rooms = useLoadRooms();

  return (
    <>
      <LocalHeader>
        <BackBtn />
        채팅
        <div />
      </LocalHeader>
      <MainContainerWithNav>
        <SList>
          {rooms?.map((room) => (
            <ChatComponent key={room.chatId} room={room} />
          ))}
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
