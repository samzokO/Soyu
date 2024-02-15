import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import font from '../../styles/font';
import useLoadImg from '../../hooks/useLoadImg';
import { useTimeStamp } from '../../hooks/useTimeStamp';

function ChatComponent({ room }) {
  const memberId = localStorage.getItem('memberId');
  const navigate = useNavigate();
  const date = useTimeStamp(room?.lastDate);
  const [img, loadImg] = useLoadImg();

  useEffect(() => {
    loadImg(room.imageResponses);
  }, []);

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

const SWrap = styled.div`
  display: flex;
  gap: 10px;
  width: 100%;
  white-space: nowrap;
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
