import styled from 'styled-components';

const Label = styled.div`
  margin: 2px 0px 0px 0px;
  white-space: nowrap;
`;

function BtnLabel({ content }) {
  return <Label>{content}</Label>;
}

export default BtnLabel;
