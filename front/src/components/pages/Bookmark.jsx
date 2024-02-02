import styled from 'styled-components';
import BottomNav from '../molecules/BottomNav';
import BookmarkTab from '../molecules/BookmarkTab';
import Goods from '../molecules/Goods';
import useManageTab from '../../hooks/useManageTab';
import Station from '../molecules/Station';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import { MainContainerWithNav } from '../../styles/Maincontainer';

function Bookmark() {
  const [state, handler] = useManageTab();

  return (
    <>
      <LocalHeader>
        <BackBtn />내 소유
        <div />
      </LocalHeader>
      <SBookmarkTap>
        <BookmarkTab state={state} handler={handler} />
      </SBookmarkTap>
      <MainContainerWithNav>
        {state === 'heart' ? (
          <ul>
            <Goods />
            <Goods />
            <Goods />
            <Goods />
            <Goods />
            <Goods />
          </ul>
        ) : (
          <Station />
        )}
      </MainContainerWithNav>
      <BottomNav />
    </>
  );
}

export default Bookmark;

const SBookmarkTap = styled.nav`
  margin-top: 44px;
`;
