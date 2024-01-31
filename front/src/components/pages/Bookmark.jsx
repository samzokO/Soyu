import styled from 'styled-components';
import BottomNav from '../molecules/BottomNav';
import BookmarkTab from '../molecules/BookmarkTab';
import Goods from '../molecules/Goods';

function Bookmark() {
  return (
    <>
      <div>HEAD</div>
      <BookmarkTab />
      <SMainWrap>
        <ul>
          <Goods />
          <Goods />
          <Goods />
          <Goods />
          <Goods />
          <Goods />
        </ul>
      </SMainWrap>
      <BottomNav />
    </>
  );
}
export default Bookmark;
const SMainWrap = styled.main`
  padding: 0 16px;
`;
