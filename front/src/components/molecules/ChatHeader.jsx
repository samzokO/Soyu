import styled from 'styled-components';
import GoodsHeader from './GoodsHeader';
import color from '../../styles/color';

function ChatHeader() {
  return (
    <SDiv>
      <SHeader>
        <div>뒤로가기 아이콘</div>
        <div>유저명</div>
        <div>추가 정보 아이콘</div>
      </SHeader>
      <GoodsHeader />
    </SDiv>
  );
}

export default ChatHeader;

const SDiv = styled.div`
  position: sticky;
  top: 0;
  width: 100%;
  background-color: ${color.bgColor};
`;

const SHeader = styled.header`
  display: flex;
  align-items: center;
  justify-content: space-around;
  border-bottom: 1px solid ${color.grayScale200};
`;
