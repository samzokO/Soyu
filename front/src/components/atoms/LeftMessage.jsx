import styled from 'styled-components';
import font from '../../styles/font';
import color from '../../styles/color';

function LeftMessage() {
  return (
    <SWrap>
      <SImg src="" alt="상대방 프로필 사진" />
      <SP>
        채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅채팅
      </SP>
    </SWrap>
  );
}

export default LeftMessage;

const SWrap = styled.div`
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
`;

const SImg = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 20px;

  // for dx
  border: 1px solid black;
`;

const SP = styled.p`
  max-width: 250px;
  ${font.Body1}
  background-color: ${color.grayScale200};
  border-radius: 10px;
  padding: 10px;
`;
