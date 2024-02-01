import styled from 'styled-components';
import BottomNav from '../molecules/BottomNav';
import ChatRoom from '../molecules/ChatRoom';

function ChatList() {
  return (
    <>
      <div>Header</div>
      <SWrap>
        <ul>
          <ChatRoom />
        </ul>
      </SWrap>
      <BottomNav />
    </>
  );
}

export default ChatList;

const SWrap = styled.div`
  padding: 16px;
`;
