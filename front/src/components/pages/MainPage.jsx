import styled from 'styled-components';
import { Link } from 'react-router-dom';
import Logo from '../../assets/icons/Logo.svg';
import Search from '../../assets/icons/material_16/search.svg';
import Category from '../../assets/icons/material_24/category.svg';
import Notification from '../../assets/icons/material_24/notification.svg';
import LocalHeader from '../molecules/LocalHeader';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import BottomNav from '../molecules/BottomNav';
import useItemList from '../../hooks/useItemList';
import WriteBtn from '../atoms/WriteBtn';
import ItemList from '../molecules/ItemList';

function MainPage() {
  const data = useItemList();
  return (
    <>
      <LocalHeader>
        <Link to="/">
          <SLogo src={Logo} alt="로고" />
        </Link>

        <SMenuBox>
          <Link to="/search">
            <SMenu src={Search} alt="검색버튼" />
          </Link>
          <Link to="/">
            <SMenu src={Category} alt="카테고리버튼" />
          </Link>
          <Link to="/notification">
            <SMenu src={Notification} alt="알림버튼" />
          </Link>
        </SMenuBox>
      </LocalHeader>
      <MainContainerWithNav>
        <ItemList title="최근 올라온 매물" data={data} />
      </MainContainerWithNav>
      <BottomNav />
      <Link to="/login">
        <SBtn />
      </Link>
    </>
  );
}

const SBtn = styled(WriteBtn)`
  position: absolute;
  top: 100px;
`;

const SLogo = styled.img`
  width: 32px;
  height: 32px;
`;

const SMenu = styled.img`
  width: 24px;
  height: 24px;
`;

const SMenuBox = styled.div`
  position: relative;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
`;

export default MainPage;
