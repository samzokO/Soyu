import styled from 'styled-components';
import LeftIcon from '../../assets/icons/Icon_24/LeftIcon';
import TextBtn from '../atoms/TextBtn';
import BackBtn from '../atoms/BackBtn';

const Header = styled.nav`
  ${({ theme }) => theme.font.Title};
  padding: 0px 10px;
  height: 44px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  white-space: nowrap;
  border-bottom: 1px solid ${({ theme }) => theme.color.grayScale200};
  @media screen and (min-width: 769px) {
  }
`;
/** 뒤로가기를 포함한 로컬 헤더
 * @params (string) TitleText - 제목,
 * @params (string) BtnText : 버튼내용 */
function LocalHeader({ TitleText, BtnText }) {
  return (
    <Header>
      <BackBtn>
        <LeftIcon />
      </BackBtn>
      {TitleText}
      <TextBtn content={BtnText} />
    </Header>
  );
}

export default LocalHeader;
