import { styled } from 'styled-components';
import InputBox from '../atoms/InputBox';

const Field = styled.div`
  display: flex;
  flex-direction: column;
  max-width: 345px;
  height: 52px;
  ${({ theme }) => theme.font.Body2};
`;

const Label = styled.div`
  margin: 0px 0px 0px 3px;
`;

/** 텍스트 필드 with title
 * @params (string) title - 입력필드 라벨
 * @params (string) type - 입력필드 타입
 * @params (string) id - 입력필드 아이디
 * @params (string) placeholder - 입력필드 플레이스홀더
 * @params (string) image - 입력필드에 넣을 아이콘 주소
 * @returns input
 */
function TextField({ title, type, id, placeholder, image }) {
  return (
    <Field>
      <Label>{title}</Label>
      <InputBox type={type} id={id} placeholder={placeholder} image={image} />
    </Field>
  );
}

export default TextField;
