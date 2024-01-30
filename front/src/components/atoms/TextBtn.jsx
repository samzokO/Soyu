import styled from 'styled-components';

const Btn = styled.button``;

function TextBtn({ children, content }) {
  return (
    <Btn>
      {children}
      {content}
    </Btn>
  );
}

export default TextBtn;
