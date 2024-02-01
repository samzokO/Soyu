import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Badge from '../atoms/Badge';
import Favorite from '../../assets/icons/material_24/favorite.svg';
import Button from '../atoms/Button';
import font from '../../styles/font';
import color from '../../styles/color';

function BuyGoods() {
  return (
    <SLiWrap>
      <Link to="/">
        <SSpaceBetween>
          <SFlexWrap>
            <SImg src="#" alt="물건 사진" />
            <div>
              <SH3>물건 이름</SH3>
              <SPrice>10,000,000원</SPrice>
              <SFlexWrap>
                <SHeart src={Favorite} alt="찜 아이콘" />
                <SHeartCount>3</SHeartCount>
              </SFlexWrap>
            </div>
          </SFlexWrap>
          <Badge status="5" />
        </SSpaceBetween>
      </Link>
      <Button Handler="" type="1">
        오프라인 DP 예약하기
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
  border-bottom: 1px solid ${color.grayScale200};
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
  ${font.Subtitle};
  margin-bottom: 8px;
`;

const SPrice = styled.p`
  ${font.Body1}
  margin-bottom: 8px;
`;

const SHeart = styled.img`
  width: 16px;
  height: 16px;
  margin-right: 5px;
`;

const SHeartCount = styled.span`
  ${font.Body1}
`;
