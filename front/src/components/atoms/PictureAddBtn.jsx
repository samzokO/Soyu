import styled from 'styled-components';
import theme from '../../styles/theme';

function PictureAddBtn() {
  return (
    <div>
      <SLabel>사진 추가</SLabel>
      <SBtn>+</SBtn>
    </div>
  );
}

const SBtn = styled.button`
  width: 50px;
  height: 50px;
  background-color: ${theme.color.grayScale200};
  border-radius: 7px;
`;

const SLabel = styled.div`
  margin-left: 3px;
  ${theme.font.Body2};
`;

export default PictureAddBtn;
