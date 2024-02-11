import styled from 'styled-components';
import { Link } from 'react-router-dom';
import theme from '../../styles/theme';
import Badge from './Badge';
import { useTimeStamp } from '../../hooks/useTimeStamp';

/** 물품 리스트 아이템
 * @params (string)img - 이미지 주소
 * @params (string)title - 제목
 * @params (date)regDate - 등록날짜
 * @params (string)location - 위치?
 * @params (string)boxLocation - 보관된 소유박스 위치
 * @params (number)boxNumber - 몇번째 칸
 * @params (number) itemStatus - 상태 (0~5)
 * @params (number) price - 가격
 */
function ItemBox({
  // img,
  itemId,
  title,
  regDate,
  itemCategories,
  boxLocation = '',
  boxNumber,
  itemStatus = 5,
  price,
}) {
  // 금액 , 삽입
  const Price = String(price).replace(/\B(?=(\d{3})+(?!\d))/g, ',');
  const date = useTimeStamp(regDate);
  return (
    <SFlexItem to={`/item/${itemId}`}>
      <SFlexCenterGap>
        <SImgContainer $url={0} src="상품 이미지" />
        <SFlexWrapColumn>
          <SFlexColumnGap>
            <p>{title}</p>
            <SFontsize>
              <p>
                {itemCategories}
                <Sbody2> - {date}</Sbody2>
              </p>
              <SFlexCenterGap>
                <p>{boxLocation}</p>
                <p>{boxNumber}</p>
              </SFlexCenterGap>
            </SFontsize>
          </SFlexColumnGap>
          <SFlexCenterGap>
            {itemStatus !== 5 && <Badge status={itemStatus} />}
            <div>{Price}원</div>
          </SFlexCenterGap>
        </SFlexWrapColumn>
      </SFlexCenterGap>
    </SFlexItem>
  );
}

const Sbody2 = styled.span`
  ${theme.font.Body2}
`;

export const SFlexCenter = styled.div`
  display: flex;
  align-items: center;
`;

const SFlexItem = styled(Link)`
  display: flex;
  align-items: center;
  height: 127px;
  border-bottom: 1px solid ${theme.color.grayScale200};
`;

export const SFlexCenterGap = styled(SFlexCenter)`
  gap: 10px;
`;

const SFlexColumn = styled.div`
  display: flex;
  flex-direction: column;
`;

const SFlexColumnGap = styled(SFlexColumn)`
  gap: 3px;
`;

const SFlexWrapColumn = styled(SFlexColumn)`
  padding: 5px 0px;
  justify-content: space-between;
  height: 100px;
`;

const SImgContainer = styled.div`
  width: 106px;
  height: 106px;
  border: 1px solid black;
  border-radius: 7px;
  text-align: center;
  background-image: url();
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
`;

const SFontsize = styled.div`
  ${theme.font.Body1}
`;

export default ItemBox;
