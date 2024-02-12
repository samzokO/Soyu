import BottomNav from '../molecules/BottomNav';
import LocalHeader from '../molecules/LocalHeader';
import Button from '../atoms/Button';
import InfoList from '../molecules/InfoList';
import DealList from '../molecules/DealList';
import Profile from '../molecules/Profile';
import BackBtn from '../atoms/BackBtn';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import useMyPage from '../../hooks/useMypage';
import useLogout from '../../hooks/useLogout';
import useSignout from '../../hooks/useSignout';

function MyPage() {
  const data = useMyPage();
  const logout = useLogout();
  const signout = useSignout();
  return (
    <>
      <LocalHeader>
        <BackBtn />
        My Page
        <div />
      </LocalHeader>
      <MainContainerWithNav>
        <Profile
          nickName={data?.nickName ?? data?.name}
          bankName={data?.bankName ?? '계좌 등록후 판매거래가 가능합니다'}
          accountNumber={data?.accountNumber}
        />
        <DealList />
        <InfoList />
        <Button type="0" onClick={logout}>
          로그아웃
        </Button>
        <Button type="5" onClick={signout}>
          회원탈퇴
        </Button>
      </MainContainerWithNav>
      <BottomNav />
    </>
  );
}

export default MyPage;
