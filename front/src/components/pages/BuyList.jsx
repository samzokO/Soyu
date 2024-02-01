import styled from 'styled-components';
import BuyGoods from '../molecules/BuyGoods';

function BuyList() {
  return (
    <>
      <div>Header 자리</div>
      <div>Tab 자리</div>
      <SWrap>
        <BuyGoods />
      </SWrap>
    </>
  );
}

export default BuyList;

const SWrap = styled.ul`
  padding: 16px;
`;
