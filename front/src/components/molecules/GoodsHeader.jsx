import { useNavigate, useParams } from 'react-router-dom';
import { useEffect } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import styled from 'styled-components';
import font from '../../styles/font';
import Button from '../atoms/Button';
import color from '../../styles/color';
import useLoadItem from '../../hooks/useLoadItem';
import useLoadImg from '../../hooks/useLoadImg';

function GoodsHeader({ itemId }) {
  const [imageURL, name, price, itemStatus] = useLoadItem(itemId);
  const navigate = useNavigate();
  const { chatId } = useParams();
  const [img, loadImg] = useLoadImg();

  const itemStatusArr = {
    TRADE_RESERVE: {
      status: 0,
      content: '예약중',
      warning: '예약된 물품이에요',
    },
    DP: { status: 1, content: 'DP 전시중', warning: '예약된 물품이에요' },
    DP_RESERVE: {
      status: 2,
      content: 'DP 예정',
      warning: '곧 DP 될 예정이에요!',
    },
    SOLD: { status: 3, content: '거래완료', warning: '거래된 물품이에요' },
    WITHDRAW: {
      status: 4,
      content: '회수 대기중',
      warning: '회수 예정인 물품이에요',
    },
    ONLINE: {
      status: 5,
      content: '거래 약속 하기',
    },
  };

  useEffect(() => {
    loadImg(imageURL);
  }, [imageURL]);

  return (
    <SFlex>
      <SImg src={img} alt="물건 사진" />
      <SDiv>
        <SH2>{name}</SH2>
        <SPrice>{price}원</SPrice>
      </SDiv>
      {itemStatus && (
        <Button
          type={itemStatusArr[itemStatus].status}
          onClick={() => {
            if (itemStatusArr[itemStatus].status === 5) {
              navigate(`/station/${chatId}`);
            } else {
              toast.error(`${itemStatusArr[itemStatus].warning}`, {
                position: 'top-center',
              });
            }
          }}
        >
          {itemStatusArr[itemStatus].content}
        </Button>
      )}
    </SFlex>
  );
}
export default GoodsHeader;

const SDiv = styled.div`
  width: 100%;
`;

const SFlex = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0 16px;
  border-bottom: 1px solid ${color.grayScale200};
`;

const SImg = styled.img`
  min-width: 50px;
  height: 50px;
  border-radius: 7px;
`;

const SH2 = styled.h2`
  ${font.Title}
  overflow: hidden;
`;

const SPrice = styled.p`
  ${font.Body1}
  width: auto;
  overflow: hidden;
`;
