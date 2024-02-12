import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import { over } from 'stompjs';
import * as SockJS from 'sockjs-client';

import ChatBottomNav from '../molecules/ChatBottomNav';
import ChatHeader from '../molecules/ChatHeader';
import ChatMessageList from '../molecules/ChatMessageList';

function ChatRoom({ state }) {
  console.log(state);
  // 필요 데이터
  // 1. 상품 헤더 : 물품 이미지(imageResponse), 물품명(title), 물품 가격(price)
  // 2. 메시지 리스트 : 상대방 프로필 이미지(sellerProfileImageResponse), 채팅 내용 (messageResponses[])

  const { chatId } = useParams();

  const client = useRef(null);

  const connect = () => {
    const Sock = new SockJS('http:/i10b311.p.ssafy.io:8080/ws/chat');
    client.current = over(Sock);
    client.current.connect(
      {},
      () =>
        client.current.subscribe(`/sub/message/${chatId}`, (data) => {
          console.log('data received');
          console.log(data);
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
    connect();
  }, []);

  return (
    <>
      <ChatHeader itemId={state} />
      <ChatMessageList />
      <ChatBottomNav sendHandler={send} />
    </>
  );
}

export default ChatRoom;
