import styled from 'styled-components';
import { Link } from 'react-router-dom';
import Logo from '../../assets/icons/Logo.svg';
import Search from '../../assets/icons/material_16/search.svg';
import Category from '../../assets/icons/material_24/category.svg';
import Notification from '../../assets/icons/material_24/notification.svg';
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
          <Link to="/">
            <SMenu src={Search} alt="로고" />
          </Link>
          <Link to="/">
            <SMenu src={Category} alt="로고" />
          </Link>
          <Link to="/">
            <SMenu src={Notification} alt="로고" />
          </Link>
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

const SMenu = styled.img`
  width: 24px;
  height: 24px;
`;

const SMenuBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
`;

export default MainPage;
