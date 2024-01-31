import styled from 'styled-components';

const Btn = styled.button``;

function TextBtn({ content }) {
  return <Btn>{content}</Btn>;
}

export default TextBtn;
