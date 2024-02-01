import styled from 'styled-components';
import theme from '../../styles/theme';
import Badge from '../atoms/Badge';

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
  img,
  title,
  regDate,
  location,
  boxLocation = '',
  boxNumber,
  itemStatus = 0,
  price,
}) {
  return (
    <SFlexItem>
      <SFlexCenterGap>
        <SImgContainer url={img} src="상품 이미지" />
        <SFlexWrapColumn>
          <SFlexColumnGap>
            <p>{title}</p>
            <SFontsize>
              <p>
                {location} - {regDate}
              </p>
              <SFlexCenterGap>
                <p>{boxLocation}</p>
                <p>{boxNumber}</p>
              </SFlexCenterGap>
            </SFontsize>
          </SFlexColumnGap>
          <SFlexCenterGap>
            <Badge type={itemStatus} />
            <div>{price}원</div>
          </SFlexCenterGap>
        </SFlexWrapColumn>
      </SFlexCenterGap>
    </SFlexItem>
  );
}

const SFlexCenter = styled.div`
  display: flex;
  align-items: center;
`;

const SFlexItem = styled(SFlexCenter)`
  height: 127px;
  border-bottom: 1px solid ${theme.color.grayScale200};
`;

const SFlexCenterGap = styled(SFlexCenter)`
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

const SImgContainer = styled.img`
  width: 106px;
  height: 106px;
  border: 1px solid black;
  border-radius: 7px;
  text-align: center;
`;

const SFontsize = styled.div`
  ${theme.font.Body1}
`;

export default ItemBox;
