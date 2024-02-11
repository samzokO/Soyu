import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Badge from '../atoms/Badge';
import Favorite from '../../assets/icons/material_24/favorite.svg';
import Button from '../atoms/Button';
import theme from '../../styles/theme';
import { useTimeStamp } from '../../hooks/useTimeStamp';

function BuyGoods({ data, list }) {
  const price = data.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  const date = useTimeStamp(data.regDate);
  return (
    <SLiWrap>
      <Link to={`/item/${data.itemId}`}>
        <SSpaceBetween>
          <SFlexWrap>
            <SImg src="#" alt="물건 사진" />
            <div>
              <SH3>{data.itemId}</SH3>
              <SPrice>{price}원</SPrice>
              <SFlexWrap>
                <SHeart src={Favorite} alt="찜 아이콘" />
                <SHeartCount>{data.likeCount}</SHeartCount>
              </SFlexWrap>
            </div>
          </SFlexWrap>
          <Badge status={data.itemStatus === 'buyList' ? '3' : '5'} />
        </SSpaceBetween>
      </Link>
      <Button Handler="" type={list === 'buylist' ? '1' : '0'}>
        {list === 'buylist' ? '무튼구매' : '오프라인 DP 예약하기'}
      </Button>
    </SLiWrap>
  );
}

export default BuyGoods;

const SFlexWrap = styled.div`
  display: flex;
  align-items: center;
`;

const SLiWrap = styled.li`
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
  height: 60px;
  margin-right: 20px;
`;

const SH3 = styled.h3`
  ${theme.font.Subtitle};
  margin-bottom: 8px;
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
