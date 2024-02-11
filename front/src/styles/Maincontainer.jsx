import styled from 'styled-components';
import { Header } from '../components/molecules/LocalHeader';
import { SNav } from '../components/molecules/BottomNav';

// 함수를 사용하여 height 값을 추출하는 코드
const getHeightValue = (component, index) => {
  const heightStyle =
    component.componentStyle.rules[index].match(/height:\s*(\d+px);/);
  return heightStyle ? heightStyle[1] : null;
};

// Header와 SNav에서 각각 height 값을 추출
const headerHeight = getHeightValue(Header, 4);
const sNavHeight = getHeightValue(SNav, 0);

export const MainContainerWithoutNav = styled.main`
  margin-top: 44px;
  margin-left: auto;
  margin-right: auto;
  max-width: 1024px;
  padding: 0px 15px;
  position: relative;
  min-height: calc(100vh - ${sNavHeight});
`;

export const MainContainerWithNav = styled(MainContainerWithoutNav)`
  margin-bottom: 53px;
  min-height: calc(100vh - (${headerHeight} + ${sNavHeight}));
`;
