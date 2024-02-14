import { styled } from 'styled-components';
import InputBox from '../atoms/InputBox';

/** 텍스트 필드 with title
 * @params (string) title - 입력필드 라벨
 * @params (string) type - 입력필드 타입
 * @params (string) id - 입력필드 아이디
 * @params (string) placeholder - 입력필드 플레이스홀더
 * @params (string) image - 입력필드에 넣을 아이콘 주소
 * @returns input
 */
function TextField({
  title,
  type,
  name,
  id,
  placeholder,
  image,
  onChange,
  defaultValue,
}) {
  return (
    <SField>
      <SLabel>{title}</SLabel>
      <InputBox
        type={type}
        id={id}
        name={name}
        placeholder={placeholder}
        image={image}
        onChange={onChange}
        defaultValue={defaultValue}
      />
    </SField>
  );
}

const SField = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  max-width: 768px;
  ${({ theme }) => theme.font.Body2};
`;

const SLabel = styled.div`
  margin: 0px 0px 0px 3px;
`;

export default TextField;
