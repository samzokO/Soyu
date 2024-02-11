import styled from 'styled-components';

function TextArea({ title, name, placeholder, onChange }) {
  return (
    <SWrap>
      <SLabel>{title}</SLabel>
      <SArea name={name} placeholder={placeholder} onChange={onChange} />
    </SWrap>
  );
}

const SWrap = styled.div`
  ${({ theme }) => theme.font.Body2};
`;

const SArea = styled.textarea`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 150px;
  max-width: 768px;
  padding: 10px 15px;
  border-radius: 7px;
  border: 1px solid ${({ theme }) => theme.color.grayScale400};
  placeholder: ${(props) => props.placeholder};

  &::placeholder {
    ${({ theme }) => theme.font.Body2};
  }
`;

const SLabel = styled.div`
  margin: 0px 0px 0px 3px;
`;

export default TextArea;
