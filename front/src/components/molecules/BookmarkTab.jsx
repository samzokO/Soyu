import styled from 'styled-components';

function BookmarkTab({ children }) {
  return <STab>{children}</STab>;
}

export default BookmarkTab;

const STab = styled.nav`
  width: 100%;
  display: flex;
`;
