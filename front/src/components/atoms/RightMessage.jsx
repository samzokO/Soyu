import styled from 'styled-components';
import font from '../../styles/font';
import color from '../../styles/color';

function RightMessage() {
  return (
    <SWrap>
      <SP>
        채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅
      </SP>
    </SWrap>
  );
}

export default RightMessage;

const SP = styled.p`
  max-width: 250px;
  ${font.Body1}
  background-color: ${color.primaryColor};
  color: ${color.white};
  border-radius: 10px;
  padding: 10px;
`;

const SWrap = styled.div`
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
  justify-content: right;
`;
