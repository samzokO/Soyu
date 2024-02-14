import { useEffect } from 'react';
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
import useLoadImg from '../../hooks/useLoadImg';

function MyPage() {
  const data = useMyPage();
  const [image, LoadImage] = useLoadImg();
  const logout = useLogout();
  const signout = useSignout();
  useEffect(() => {
    if (data.profileImageResponse) {
      LoadImage(data.profileImageResponse);
    }
  }, [data]);
  return (
    <>
      <LocalHeader>
        <BackBtn />
        My Page
        <div />
      </LocalHeader>
      <MainContainerWithNav>
        {image && (
          <Profile
            img={null}
            nickName={data?.nickName ?? '닉네임을 등록해주세요'}
            bankName={data?.bank_name ?? '계좌 등록후 판매거래가 가능합니다'}
            accountNumber={data?.account_number}
          />
        )}
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
