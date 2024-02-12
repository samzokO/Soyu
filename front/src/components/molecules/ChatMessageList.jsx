import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import styled from 'styled-components';
import LeftMessage from '../atoms/LeftMessage';
import RightMessage from '../atoms/RightMessage';
import { getChats } from '../../api/apis';

function ChatMessageList() {
  const [chats, setChats] = useState([]);
  const [partnerImage, setPartnerImage] = useState('/');

  const { chatId } = useParams();
  const myMemberId = localStorage.getItem('memberId');

  useEffect(() => {
    (async () => {
      const { data } = await getChats(chatId);
      console.log('chat message list component : ', data);
      setChats(data.data.messageResponse);
      // myMemberId === data.data.buyerId
      //   ? buyerProfileImageResponse
      //   : sellerProfileImageResponse;
      // load image logic
    })();
  }, []);

  return (
    <SWrap>
      {chats.map((chat) =>
        myMemberId !== chat.memberId ? (
          <LeftMessage img={partnerImage}>{chat.content}</LeftMessage>
        ) : (
          <RightMessage>{chat.content}</RightMessage>
        ),
      )}
    </SWrap>
  );
}

export default ChatMessageList;

const SWrap = styled.div`
  padding: 16px;
`;
