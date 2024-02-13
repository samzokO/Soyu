import styled from 'styled-components';
import font from '../../styles/font';
import color from '../../styles/color';
import defaultImage from '../../assets/icons/Logo.svg';
import theme from '../../styles/theme';

function LeftMessage({ children, img }) {
  return (
    <SWrap>
      {img ? (
        <SImg src={img} alt="상대방 프로필 사진" />
      ) : (
        <SImg src={defaultImage} alt="상대방 프로필 사진" />
      )}

      <SP>{children}</SP>
    </SWrap>
  );
}

export default LeftMessage;

const SWrap = styled.div`
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
`;

const SImg = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 20px;
  ${theme.box}
`;

const SP = styled.p`
  max-width: 250px;
  ${font.Body1}
  background-color: ${color.grayScale200};
  border-radius: 10px;
  padding: 10px;
`;
