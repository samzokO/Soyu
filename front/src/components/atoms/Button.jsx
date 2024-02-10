import styled from 'styled-components';
import Condition from '../../styles/BadgeCondition';
import theme from '../../styles/theme';

function Button({ children, type, onClick }) {
  return (
    <SButton type="button" onClick={onClick} color={type}>
      {children}
    </SButton>
  );
}

export default Button;

const SButton = styled.button`
  background-color: ${(props) => Condition[props.color].bgColor};
  color: ${(props) => Condition[props.color].textColor};
  ${theme.font.Body1};

  display: flex;
  justify-content: center;
  align-items: center;

  border-radius: 5px;
  padding: 10px 0;
  margin: 10px 0;

  // for dx
  /* width: 90vw; */
  width: 100%;
`;
