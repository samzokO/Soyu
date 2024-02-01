import styled from 'styled-components';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import ImageSlide from '../molecules/ImageSlide';
import LocalHeader from '../molecules/LocalHeader';
import theme from '../../styles/theme';
import ImageCircle from '../atoms/ImageCircle';
import DetailBottomNav from '../molecules/DetailBottomNav';
import HeartIcon from '../../assets/icons/Icon_24/HeartIcon';
import TextBtn from '../atoms/TextBtn';

function ProductDetail({ title, content, price, regDate }) {
  return (
    <>
      <LocalHeader>
        <BackBtn />
        <div />
      </LocalHeader>

      <ImageSlide />
      <SMainContainer>
        <SProfile>
          <ImageCircle />
          <div>
            <SContent>직거래 살인마</SContent>
            <SCategory>직거래만 합니다</SCategory>
          </div>
        </SProfile>
        <STitle>직거래 하실분 {title}</STitle>
        <SCategory> 직거래 | 24.02.01{regDate}</SCategory>
        <SContent>
          {content} 직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다 직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다직거래만 합니다직거래만
          합니다직거래만 합니다직거래만 합니다ㅍ{' '}
        </SContent>
        <DetailBottomNav>
          <HeartIcon />
          <div>1000{price}원</div>
          <TextBtn>채팅하기</TextBtn>
        </DetailBottomNav>
      </SMainContainer>
    </>
  );
}

const SMainContainer = styled(MainContainerWithNav)`
  margin: 10px 0px 53px 0px;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const SProfile = styled.div`
  height: 50px;
  border-bottom: 1px solid ${theme.color.grayScale200};
  display: flex;
  align-items: center;
  gap: 20px;
`;

const STitle = styled.div`
  ${theme.font.Title}
`;

const SCategory = styled.div`
  ${theme.font.Body2}
  color: ${theme.color.grayScale300}
`;

const SContent = styled.div`
  ${theme.font.Body1}
`;
export default ProductDetail;
