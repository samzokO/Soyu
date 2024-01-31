import { Link } from 'react-router-dom';
import styled from 'styled-components';
import theme from '../../styles/theme';

/** 하단바 네비 버튼 */
function BottomNavBtn({ children, url, active }) {
  return (
    <SNavBtn to={url} active={active}>
      {children}
    </SNavBtn>
  );
}

const Palette = {
  black: theme.color.grayScale500,
  gray: theme.color.grayScale300,
};

const SNavBtn = styled(Link)`
  width: 100%;
  padding: 6px 8px;
  display: flex;
  flex-direction: column;
  align-items: center;
  border-top: 1.5px solid ${(props) => Palette[props.active]};
`;

export default BottomNavBtn;
