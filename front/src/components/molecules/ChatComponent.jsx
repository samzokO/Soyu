import { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import font from '../../styles/font';
import theme from '../../styles/theme';
import useLoadImg from '../../hooks/useLoadImg';
import { useTimeStamp } from '../../hooks/useTimeStamp';

function ChatComponent({ room }) {
  const memberId = localStorage.getItem('memberId');
  const navigate = useNavigate();
  const date = useTimeStamp(room?.lastDate);
  const [img, LoadImg] = useLoadImg();
  useEffect(() => {
    LoadImg(room.imageResponses);
  });
  return (
    <li>
      <button
        type="button"
        onClick={() =>
          navigate(`/chat/${room?.chatId}`, { state: { itemId: room?.itemId } })
        }
      >
        <SWrap>
          <SWrap>
            {img && <SImg src={img} alt="물품 이미지" />}
            <div>
              <SH2>
                {memberId !== room?.buyerId
                  ? room?.buyerNickname
                  : room?.sellerNickname}
              </SH2>
              <SP>{room?.lastMessage}</SP>
            </div>
          </SWrap>
          <p>{date}</p>
        </SWrap>
      </button>
    </li>
  );
}

export default ChatComponent;

const SBody2 = styled.p`
  ${theme.font.Body2}
`;

const SWrap = styled.div`
  display: flex;
  gap: 10px;
  width: 100%;
  white-space: nowrap;
`;

const SWrap2 = styled(SWrap)`
  justify-content: space-between;
`;

const SImg = styled.img`
  width: 95px;
  height: 95px;
  border-radius: 7px;
`;

const SH2 = styled.h2`
  ${font.Subtitle}
  margin-bottom: 10px;
`;

const SP = styled.p`
  ${font.Body1}
`;

const SLi = styled(Link)`
  list-style: none;
  padding: 12px;
  display: flex;
  gap: 25px;
  min-width: 400px;
  max-width: 600px;
  position: relative;
  border-radius: 7px;
  transition: all 0.3s cubic-bezier(0, 0, 0.5, 1);
  box-shadow: 1px 2px 6px rgba(0, 0, 0, 0.08);
  &:hover {
    box-shadow: 2px 4px 16px rgba(0, 0, 0, 0.16);
    cursor: pointer;
  }
`;
