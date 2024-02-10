import { styled } from 'styled-components';

function Keypad({ children }) {
  return <SPad>{children}</SPad>;
}

const SPad = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 10px;
  padding: 10px;
`;

export default Keypad;
