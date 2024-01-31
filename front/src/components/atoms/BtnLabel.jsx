import styled from 'styled-components';

function BtnLabel({ content }) {
  return <SLabel>{content}</SLabel>;
}

const SLabel = styled.div`
  margin: 2px 0px 0px 0px;
  white-space: nowrap;
`;

export default BtnLabel;
