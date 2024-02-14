import { useEffect } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { motion } from 'framer-motion';
import Badge from '../atoms/Badge';
import Favorite from '../../assets/icons/material_24/favorite.svg';
import Button from '../atoms/Button';
import theme from '../../styles/theme';
import { useTimeStamp } from '../../hooks/useTimeStamp';
import useLoadImg from '../../hooks/useLoadImg';
import useReservation from '../../hooks/useReservation';
import useCodeConfirm from '../../hooks/code/useCodeConfirm';

function BuyGoods({ data, list, variants, itemStatus }) {
  const price = data.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  const [img, loadImage] = useLoadImg();
  const [r, reservation] = useReservation();
  const [codeData, sellerCode, buyerCode] = useCodeConfirm();

  useEffect(() => {
    loadImage(data.imageResponses[0]);
  }, []);

  const itemStatusList = {
    TRADE_RESERVE: {
      badge: 0,
      status: list === 'buylist' ? 0 : 1,
      content:
        list === 'buylist'
          ? '구매 코드 확인하기'
          : '오프라인 판매 코드 확인하기',
    },
    DP: {
      badge: 1,
      status: list === 'buylist' ? 1 : 4,
      content: list === 'buylist' ? '구매 코드 확인하기' : '회수 코드 확인하기',
    },
    DP_RESERVE: {
      badge: 2,
      status: list === 'buylist' ? 0 : 1,
      content: list === 'buylist' ? 'DP 예정 물품입니다' : 'DP 코드 확인하기',
    },
    SOLD: {
      badge: 3,
      status: list === 'buylist' ? 4 : 3,
      content: list === 'buylist' ? '구매목록에서 지우기' : '거래완료',
    },
    WITHDRAW: {
      badge: 4,
      status: list === 'buylist' ? 3 : 4,
      content:
        list === 'buylist' ? '회수 예정 물품입니다.' : '회수 코드 확인하기',
    },
    ONLINE: {
      badge: 5,
      status: list === 'buylist' ? 0 : 0,
      content: list === 'buylist' ? '판매자와 채팅하기' : 'DP 예약하기',
    },
  };

  const makeReservation = () => {
    buyerCode(data.itemId);
  };
  return (
    <SLiWrap variants={variants}>
      <Link to={`/item/${data.itemId}`}>
        <SSpaceBetween>
          <SFlexWrap>
            {img && <SImg src={img} alt="물건 사진" />}
            <div>
              <SH3>{data.title}</SH3>
              <SPrice>{price}원</SPrice>
              <SFlexWrap>
                <SHeart src={Favorite} alt="찜 아이콘" />
                <SHeartCount>{data.likeCounts}</SHeartCount>
              </SFlexWrap>
            </div>
          </SFlexWrap>
          <Badge status={itemStatusList[itemStatus].badge} />
        </SSpaceBetween>
      </Link>
      <Button
        onClick={makeReservation}
        type={itemStatusList[itemStatus].status}
      >
        {itemStatusList[itemStatus].content}
      </Button>
    </SLiWrap>
  );
}

export default BuyGoods;

const SFlexWrap = styled.div`
  display: flex;
  align-items: center;
`;

const SLiWrap = styled(motion.li)`
  padding: 14px;
  position: relative;
  border-bottom: 1px solid ${theme.color.grayScale200};
  list-style: none;
`;

const SSpaceBetween = styled(SFlexWrap)`
  justify-content: space-between;
  align-items: start;
`;

const SImg = styled.img`
  width: 60px;
  min-width: 60px;
  height: 60px;
  margin-right: 20px;
  /* background-image: url(${(props) => props.img}); */
`;

const SH3 = styled.h3`
  ${theme.font.Subtitle};
  margin-bottom: 8px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const SPrice = styled.p`
  ${theme.font.Body1}
  margin-bottom: 8px;
`;

const SHeart = styled.img`
  width: 16px;
  height: 16px;
  margin-right: 5px;
`;

const SHeartCount = styled.span`
  ${theme.font.Body1}
`;
