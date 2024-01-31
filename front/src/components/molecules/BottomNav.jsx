import React, { useState } from 'react';
import styled from 'styled-components';
import BottomNavBtn from '../atoms/BottomNavBtn';
import BtnLabel from '../atoms/BtnLabel';
import HomeOnIcon from '../../assets/icons/Icon_24/HomeOnIcon';
import SendIcon from '../../assets/icons/Icon_24/SendIcon';
import PersonIcon from '../../assets/icons/Icon_24/PersonIcon';
import HeartIcon from '../../assets/icons/Icon_24/HeartIcon';
import LocationIcon from '../../assets/icons/Icon_24/LocationIcon';
import theme from '../../styles/theme';

const icons = {
  main: { icon: HomeOnIcon, label: '메인', url: '' },
  heart: { icon: HeartIcon, label: '내 소유', url: '' },
  map: { icon: LocationIcon, label: '지도', url: '' },
  chat: { icon: SendIcon, label: '채팅', url: '' },
  my: { icon: PersonIcon, label: '마이', url: '' },
};

/** 하단 네비 */
function BottomNav() {
  // eslint-disable-next-line
  const [active, setActive] = useState('main');

  return (
    <SNav>
      {Object.keys(icons).map((key) => (
        <BottomNavBtn
          key={key}
          url={icons[key].url}
          active={active === key ? 'black' : 'gray'}
        >
          {React.createElement(icons[key].icon, {
            active: active === key ? 'black' : 'gray',
          })}
          <BtnLabel content={icons[key].label} />
        </BottomNavBtn>
      ))}
    </SNav>
  );
}

const SNav = styled.nav`
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: space-around;
  background-color: ${theme.color.bgColor};
  ${theme.font.BtnLabel};
  @media screen and (min-width: 769px) {
  }
`;

export default BottomNav;
