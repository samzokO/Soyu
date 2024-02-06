import React from 'react';
import styled from 'styled-components';
import BottomNavBtn from '../atoms/BottomNavBtn';
import BtnLabel from '../atoms/BtnLabel';
import HomeOnIcon from '../../assets/icons/material_24/home.svg';
import SendIcon from '../../assets/icons/material_24/send.svg';
import PersonIcon from '../../assets/icons/material_24/person.svg';
import HeartIcon from '../../assets/icons/material_24/favorite.svg';
import LocationIcon from '../../assets/icons/material_24/location.svg';
import theme from '../../styles/theme';

const icons = {
  main: { icon: HomeOnIcon, label: '메인', url: '/' },
  heart: { icon: HeartIcon, label: '내 소유', url: '/bookmark' },
  map: { icon: LocationIcon, label: '지도', url: '/map' },
  chat: { icon: SendIcon, label: '채팅', url: '/chat' },
  my: { icon: PersonIcon, label: '마이', url: '/mypage' },
};

/** 하단 네비 */
function BottomNav() {
  return (
    <SNav>
      {Object.keys(icons).map((key) => (
        <BottomNavBtn
          key={key}
          url={icons[key].url}
          active={key === 'main' ? 'black' : 'gray'} // 기본적으로 'main'이 active일 경우 'black', 그 외는 'gray'
        >
          {React.createElement('img', {
            src: `${icons[key].icon}`,
            alt: `${icons[key].label} 아이콘`,
            fill: key === icons[key] ? 'black' : 'gray', // 'main'이 active일 경우 아이콘의 fill을 'black', 그 외는 'gray'
          })}
          <BtnLabel content={icons[key].label} />
        </BottomNavBtn>
      ))}
    </SNav>
  );
}

export const SNav = styled.nav`
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  height: 53px;
  justify-content: space-around;
  background-color: ${theme.color.bgColor};
  ${theme.font.BtnLabel};
  @media screen and (min-width: 769px) {
  }
`;

export default BottomNav;
