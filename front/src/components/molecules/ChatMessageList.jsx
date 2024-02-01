import styled from 'styled-components';
import LeftMessage from '../atoms/LeftMessage';
import RightMessage from '../atoms/RightMessage';

function ChatMessageList() {
  return (
    <SWrap>
      <LeftMessage />
      <RightMessage />
      <LeftMessage />
      <RightMessage />
      <LeftMessage />
      <RightMessage />
    </SWrap>
  );
}

export default ChatMessageList;

const SWrap = styled.div`
  padding: 16px;
`;
