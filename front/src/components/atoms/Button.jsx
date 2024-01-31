import styled from 'styled-components';
import Condition from '../../styles/BadgeCondition';
import theme from '../../styles/theme';

function Button({ children, Handler, type }) {
  return (
    <SButton type="button" onClick={Handler} status={type}>
      {children}
    </SButton>
  );
}

export default Button;

const SButton = styled.button`
  background-color: ${(props) => Condition[props.status].bgColor};
  color: ${(props) => Condition[props.status].textColor};
  ${theme.font.Body1};

  display: flex;
  justify-content: center;
  align-items: center;

  border-radius: 5px;
  padding: 10px 0;
  margin: 10px 0;

  width: 90vw;
`;
