import styled from 'styled-components';
import { Link } from 'react-router-dom';
import Logo from '../../assets/icons/Logo.svg';
import LocalHeader from '../molecules/LocalHeader';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import BottomNav from '../molecules/BottomNav';

function MainPage() {
  return (
    <>
      <LocalHeader>
        <Link to="/">
          <SLogo src={Logo} alt="로고" />
        </Link>
        <SMenuBox>
          <Link to="/">메뉴</Link>
          <Link to="/">메뉴</Link>
          <Link to="/">메뉴</Link>
        </SMenuBox>
      </LocalHeader>
      <MainContainerWithNav>dsd</MainContainerWithNav>
      <BottomNav />
    </>
  );
}

const SLogo = styled.img`
  width: 32px;
  height: 32px;
`;

const SMenuBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
`;

export default MainPage;
