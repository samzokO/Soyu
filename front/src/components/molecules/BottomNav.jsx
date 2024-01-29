<<<<<<< PATCH SET (85d335 feat: Bottom Nav amend)
import styled from 'styled-components';
import BottomNavBtn from '../atoms/BottomNavBtn';
import BtnLabel from '../atoms/BtnLabel';
import HomeOnIcon from '../../assets/icons/Icon_24/HomeOnIcon';
import SendIcon from '../../assets/icons/Icon_24/SendIcon';
import PersonIcon from '../../assets/icons/Icon_24/PersonIcon';
import HeartIcon from '../../assets/icons/Icon_24/HeartIcon';
import LocationIcon from '../../assets/icons/Icon_24/LocationIcon';

const Nav = styled.nav`
  display: flex;
  justify-content: space-between;
  ${({ theme }) => theme.font.BtnLabel};
  @media screen and (max-width: 768px) {
    margin: 0px 15px;
  }
  @media screen and (min-width: 1200px) {
    background-color: green;
  }
`;

function BottomNav() {
  return (
    <Nav>
      <BottomNavBtn url="">
        <HomeOnIcon active="true" />
        <BtnLabel content="메인" />
      </BottomNavBtn>
      <BottomNavBtn>
        <HeartIcon active="dd" />
        <BtnLabel content="내 소유" />
      </BottomNavBtn>
      <BottomNavBtn>
        <LocationIcon active="true" />
        <BtnLabel content="지도" />
      </BottomNavBtn>
      <BottomNavBtn>
        <SendIcon active="true" />
        <BtnLabel content="채팅" />
      </BottomNavBtn>
      <BottomNavBtn>
        <PersonIcon active="true" />
        <BtnLabel content="마이" />
      </BottomNavBtn>
    </Nav>
  );
}
export default BottomNav;
=======
>>>>>>> BASE      (d99123 style: 기본 색상 작성)
