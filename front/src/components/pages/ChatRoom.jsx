import ChatBottomNav from '../molecules/ChatBottomNav';
import ChatHeader from '../molecules/ChatHeader';
import ChatMessageList from '../molecules/ChatMessageList';

function ChatRoom() {
  return (
    <>
      <ChatHeader />
      <ChatMessageList />
      <ChatBottomNav />
    </>
  );
}

export default ChatRoom;
