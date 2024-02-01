import styled from 'styled-components';
import theme from '../../styles/theme';

function DetailBottomNav({ children }) {
  return <Container>{children}</Container>;
}

const Container = styled.div`
  height: 53px;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
  gap: 10px;
  justify-content: space-between;
  padding: 0px 10px;
  background-color: ${theme.color.bgColor};
`;

export default DetailBottomNav;
