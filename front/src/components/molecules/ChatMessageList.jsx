import { forwardRef } from 'react';
import styled from 'styled-components';
import LeftMessage from '../atoms/LeftMessage';
import RightMessage from '../atoms/RightMessage';

const ChatMessageList = forwardRef(({ chats, partnerImage }, ref) => {
  const myMemberId = localStorage.getItem('memberId');
  return (
    <SWrap ref={ref}>
      {chats?.map((chat, index) => {
        if (parseInt(myMemberId, 10) === chat?.memberId)
          return <RightMessage key={index}>{chat?.content}</RightMessage>;
        return (
          <LeftMessage key={index} img={partnerImage}>
            {chat?.content}
          </LeftMessage>
        );
      })}
    </SWrap>
  );
});

export default ChatMessageList;

const SWrap = styled.div`
  padding: 112px 16px 52px;
  height: 100vh;
  overflow: scroll;
`;
