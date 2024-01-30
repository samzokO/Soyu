import { Link } from 'react-router-dom';
import styled from 'styled-components';
import theme from '../../styles/theme';

const Palette = {
  black: theme.color.grayScale500,
  gray: theme.color.grayScale300,
};

const NavBtn = styled(Link)`
  width: 100%;
  padding: 6px 8px;
  text-align: center;
  border-top: 1px solid ${(props) => Palette[props.active]};
`;

/** 하단바 네비 버튼 */
function BottomNavBtn({ children, url, active }) {
  return (
    <NavBtn to={url} active={active}>
      {children}
    </NavBtn>
  );
}

export default BottomNavBtn;
