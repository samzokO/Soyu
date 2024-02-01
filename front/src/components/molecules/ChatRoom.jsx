import { Link } from 'react-router-dom';
import styled from 'styled-components';
import font from '../../styles/font';

function ChatRoom() {
  return (
    <li>
      <Link to="1">
        <SWrap>
          <SWrap>
            <SImg src="/" alt="물품 이미지" />
            <div>
              <SH2>자물쇠살인마</SH2>
              <SP>자물쇠 사실래요?</SP>
            </div>
          </SWrap>
          <p>5분전</p>
        </SWrap>
      </Link>
    </li>
  );
}

export default ChatRoom;

const SWrap = styled.div`
  display: flex;
  gap: 10px;
  justify-content: space-between;
`;

const SImg = styled.img`
  width: 95px;
  height: 80px;
`;

const SH2 = styled.h2`
  ${font.Subtitle}
  margin-bottom: 10px;
`;

const SP = styled.p`
  ${font.Body1}
`;
