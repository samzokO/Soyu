import { styled } from 'styled-components';

/** 입력필드를 반환하는 함수
 * @params (string) type - 입력필드의 타입
 * @params (string) name - 입력필드의 이름
 * @params (string) placeholder - 플레이스홀더
 */
function InputBox({ type = 'text', name, placeholder }) {
  return <SBox type={type} name={name} placeholder={placeholder} />;
}

const SBox = styled.input`
  padding: 10px 15px;
  border-radius: 7px;
  border: 1px solid ${({ theme }) => theme.color.grayScale400};
  placeholder: ${(props) => props.placeholder};

  &::placeholder {
    ${({ theme }) => theme.font.Body2};
  }
`;

export default InputBox;
