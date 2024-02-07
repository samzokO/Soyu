import BottomNav from '../molecules/BottomNav';
import LocalHeader from '../molecules/LocalHeader';
import Button from '../atoms/Button';
import InfoList from '../molecules/InfoList';
import DealList from '../molecules/DealList';
import Profile from '../molecules/Profile';
import BackBtn from '../atoms/BackBtn';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import useMyPage from '../../hooks/useMypage';

function MyPage() {
  const data = useMyPage();
  return (
    <>
      <LocalHeader>
        <BackBtn />
        My Page
        <div />
      </LocalHeader>
      <MainContainerWithNav>
        <Profile
          nickName={data.nickName}
          bankName={data.bankName}
          accountNumber={data.accountNumber}
        />
        <DealList />
        <InfoList />
        <Button type="0">로그아웃</Button>
        <Button type="5">회원탈퇴</Button>
      </MainContainerWithNav>
      <BottomNav />
    </>
  );
}

export default MyPage;
