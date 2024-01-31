import styled from 'styled-components';
import BottomNav from '../molecules/BottomNav';

import Button from '../atoms/Button';
import InfoList from '../molecules/InfoList';
import DealList from '../molecules/DealList';
import Profile from '../molecules/Profile';

function MyPage() {
  return (
    <>
      <h1>header : mypage</h1>
      <SWrap>
        <Profile />
        <DealList />
        <InfoList />
        <Button type="1">로그아웃</Button>
        <Button type="6">회원탈퇴</Button>
      </SWrap>
      <BottomNav />
    </>
  );
}

export default MyPage;

const SWrap = styled.main`
  padding: 0 16px;
`;
