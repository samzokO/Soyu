import styled from 'styled-components';
import BottomNav from '../molecules/BottomNav';
import ChatComponent from '../molecules/ChatComponent';

function ChatList() {
  return (
    <>
      <div>Header</div>
      <SWrap>
        <ul>
          <ChatComponent />
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
