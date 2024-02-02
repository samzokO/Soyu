import styled from 'styled-components';
import BuyGoods from '../molecules/BuyGoods';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import BookmarkTab from '../molecules/BookmarkTab';

function BuyList() {
  return (
    <>
      <LocalHeader>
        <BackBtn />
        구매내역
        <div />
      </LocalHeader>
      <BookmarkTab />
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
