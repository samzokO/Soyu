import styled from 'styled-components';
import { useEffect } from 'react';
import BottomNav from '../molecules/BottomNav';
import Goods from '../molecules/Goods';
import useManageTab from '../../hooks/useManageTab';
import Station from '../molecules/Station';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import { MainContainerWithNav } from '../../styles/Maincontainer';

function Bookmark() {
  const [state, Handler] = useManageTab();
  useEffect(() => {
    Handler('bookmark');
  }, []);
  return (
    <>
      <LocalHeader>
        <BackBtn />내 소유
        <div />
      </LocalHeader>
      <SBookmarkTap>
        <SBookmark onClick={() => Handler('bookmark')} current={state}>
          북마크
        </SBookmark>
        <SHeart onClick={() => Handler('heart')} current={state}>
          찜
        </SHeart>
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
  width: 100%;
`;

const SButton = styled.button`
  width: 50%;
  padding: 10px;
`;

const SBookmark = styled(SButton)`
  border-bottom: ${(props) =>
    props.current === 'bookmark' ? '1px solid #4827E9' : ''};
`;

const SHeart = styled(SButton)`
  border-bottom: ${(props) =>
    props.current === 'heart' ? '1px solid #4827E9' : ''};
`;
