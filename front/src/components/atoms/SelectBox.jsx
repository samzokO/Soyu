import { styled } from 'styled-components';

/** 입력필드를 반환하는 함수
 * @params (string) type - 입력필드의 타입
 * @params (string) name - 입력필드의 이름
 * @params (string) placeholder - 플레이스홀더
 */
function SelectBox({ id, name, options }) {
  return (
    <SBox id={id} name={name}>
      <option value="" disabled selected>
        카테고리를 선택해주세요.
      </option>
      {options.map((option) => (
        <option key={option} value={option.toUpperCase()}>
          {option}
        </option>
      ))}
    </SBox>
  );
}

const SBox = styled.select`
  padding: 10px 15px;
  border-radius: 7px;
  max-width: 768px;
  border: 1px solid ${({ theme }) => theme.color.grayScale400};
  placeholder: ${(props) => props.placeholder};
  &::placeholder {
    ${({ theme }) => theme.font.Body2};
  }
`;

export default SelectBox;
