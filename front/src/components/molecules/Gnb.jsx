import styled from 'styled-components';
import { Link } from 'react-router-dom';
import theme from '../../styles/theme';
import LogoWhite from '../../assets/Logo-White.svg';

/** Gnb !
 * children으로 아이콘버튼을 넣어주면
 * 오른쪽 메뉴 모음으로 들어감
 */
function Gnb({ children }) {
  return (
    <GNB>
      <LinkBtn>
        <Logo src={LogoWhite} alt="로고" />
      </LinkBtn>
      <MenuBox>{children}</MenuBox>
    </GNB>
  );
}

const GNB = styled.nav`
  height: 52px;
  background-color: ${theme.color.primaryColor};
  display: flex;
  padding: 0px 10px;
  justify-content: space-between;
  align-items: center;
`;

const MenuBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 12px;
`;

const LinkBtn = styled(Link)``;

const Logo = styled.img`
  width: 32px;
  height: 32px;
`;

export default Gnb;
