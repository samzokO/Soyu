import { useEffect, useRef, useState } from 'react';
import { useLocation, useParams } from 'react-router-dom';
import { over } from 'stompjs';
import * as SockJS from 'sockjs-client';

import ChatBottomNav from '../molecules/ChatBottomNav';
import ChatHeader from '../molecules/ChatHeader';
import ChatMessageList from '../molecules/ChatMessageList';

import { getChats } from '../../api/apis';
import useLoadImg from '../../hooks/useLoadImg';

function ChatRoom() {
  const { state } = useLocation();
  const [chats, setChats] = useState([]);
  const [partnerImage, setPartnerImage] = useLoadImg();
  const { chatId } = useParams();
  const myMemberId = localStorage.getItem('memberId');
  const scrollRef = useRef(null);
  const client = useRef(null);

  const connect = () => {
    const Sock = new SockJS('http:/i10b311.p.ssafy.io:8080/ws/chat');
    client.current = over(Sock);
    client.current.connect(
      {},
      () =>
        client.current.subscribe(`/sub/message/${chatId}`, (data) => {
          const message = {
            memberId: parseInt(localStorage.getItem('memberId'), 10),
            content: JSON.parse(data.body).content,
          };
          setChats((chat) => [...chat, message]);
        }),
      () => {
        console.error('connection error');
      },
    );
  };

  const send = (e, value) => {
    e.preventDefault();
    client.current.send(
      `/pub/message`,
      {},
      JSON.stringify({
        chatId,
        memberId: localStorage.getItem('memberId'),
        content: value,
      }),
    );
  };

  useEffect(() => {
    (async () => {
      const { data } = await getChats(chatId);
      setChats([...data.data.messageResponses]);
      setPartnerImage(
        myMemberId === data.data.buyerId
          ? data.data.buyerProfileImageResponse
          : data.data.sellerProfileImageResponse,
      );
      connect();
    })();
  }, []);

  const scrollToBottom = () => {
    if (scrollRef.current)
      scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  };

  useEffect(() => {
    scrollToBottom();
  }, [chats]);

  return (
    <>
      <ChatHeader itemId={state?.itemId} />
      <ChatMessageList
        chats={chats}
        setChats={setChats}
        imageURL={partnerImage}
        ref={scrollRef}
      />
      <ChatBottomNav sendHandler={send} />
    </>
  );
}

export default ChatRoom;
