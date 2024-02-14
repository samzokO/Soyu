import styled from 'styled-components';

function TextBtn({ children, type, onClick }) {
  return (
    <SBtn type={type} onClick={onClick}>
      {children}
    </SBtn>
  );
}

const SBtn = styled.button``;

export default TextBtn;
