import styled from 'styled-components';
import font from '../../styles/font';
import Button from '../atoms/Button';
import color from '../../styles/color';

function GoodsHeader() {
  return (
    <SFlex>
      <SImg src="#" alt="물건 사진" />
      <SDiv>
        <SH2>감자</SH2>
        <SPrice>300,000원</SPrice>
      </SDiv>
      <Button type="1">거래약속</Button>
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
  min-height: 50px;
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
