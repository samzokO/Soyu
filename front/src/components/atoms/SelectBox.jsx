import { styled } from 'styled-components';

/** 입력필드를 반환하는 함수
 * @params (string) type - 입력필드의 타입
 * @params (string) name - 입력필드의 이름
 * @params (string) options - 옵션들
 * @params (string) onChange - 변환시
 */
function SelectBox({ id, name, options, onChange }) {
  return (
    <SBox id={id} name={name} onChange={onChange} defaultValue="">
      <option value="" disabled>
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
`;

export default SelectBox;
