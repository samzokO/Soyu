import styled from 'styled-components';
import AddCircle from '../../assets/icons/material_36/add_circle.svg';

function WriteBtn() {
  return (
    <SBtn>
      <img src={AddCircle} alt="" />
    </SBtn>
  );
}

const SBtn = styled.button`
  position: fixed;
  bottom: 63px;
  right: 10px;
`;

export default WriteBtn;
