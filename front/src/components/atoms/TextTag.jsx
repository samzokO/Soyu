import styled from 'styled-components';
import theme from '../../styles/theme';

function TextTag({ children }) {
  return <STag type="button">{children}</STag>;
}

const STag = styled.button`
  ${theme.font.Badge};
  padding: 7px;
  min-width: 40px;
  border: 1.5px black solid;
  border-radius: 15px;
`;

export default TextTag;
