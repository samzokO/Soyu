import styled from 'styled-components';
import { Header } from '../components/molecules/LocalHeader';
import { SNav } from '../components/molecules/BottomNav';

export const MainContainerWithoutNav = styled.main`
  margin-top: 44px;
  padding: 0px 15px;
  position: relative;
  height: calc(100vh - ${Header.height});
`;

export const MainContainerWithNav = styled(MainContainerWithoutNav)`
  height: calc(100vh - ${Header.height} - ${SNav.height});
`;
