import styled from 'styled-components';
import GoodsHeader from './GoodsHeader';
import color from '../../styles/color';
import BackBtn from '../atoms/BackBtn';
import theme from '../../styles/theme';

function ChatHeader({ itemId }) {
  return (
    <SDiv>
      <SHeader>
        <BackBtn />
        <SSub>유저명</SSub>
        <div>추가 정보 아이콘</div>
      </SHeader>
      <GoodsHeader itemId={itemId} />
    </SDiv>
  );
}

export default ChatHeader;

const SSub = styled.div`
  ${theme.font.Subtitle}
`;

const SDiv = styled.div`
  position: absolute;
  top: 0;
  width: 100%;
  background-color: ${color.bgColor};
`;

const SHeader = styled.header`
  display: flex;
  align-items: center;
  padding: 10px;
  justify-content: space-between;
  border-bottom: 1px solid ${color.grayScale200};
`;
