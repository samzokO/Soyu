import { styled, css } from 'styled-components';

/** 입력필드를 반환하는 함수
 * @params (string) type - 입력필드의 타입
 * @params (string) name - 입력필드의 이름
 * @params (string) placeholder - 플레이스홀더
 * @params (string) image - 삽입할 아이콘 이미지 주소
 */
function InputBox({ type = 'text', name, placeholder, image }) {
  return (
    <SBox type={type} name={name} placeholder={placeholder} image={image} />
  );
}

const SBox = styled.input`
  padding: 10px 15px;
  border-radius: 7px;
  border: 1px solid ${({ theme }) => theme.color.grayScale400};
  placeholder: ${(props) => props.placeholder};

  &::placeholder {
    ${({ theme }) => theme.font.Body2};
  }

  ${({ image }) =>
    image &&
    css`
      &::-webkit-input-placeholder {
        background-image: url(${image});
        background-size: contain;
        background-position: 1px center;
        background-repeat: no-repeat;
        text-align: center;
        text-indent: 0;
      }
    `}
`;

export default InputBox;
