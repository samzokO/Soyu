import styled from 'styled-components';
import { useParams } from 'react-router-dom';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import ImageSlide from '../molecules/ImageSlide';
import LocalHeader from '../molecules/LocalHeader';
import theme from '../../styles/theme';
import ImageCircle from '../atoms/ImageCircle';
import DetailBottomNav from '../molecules/DetailBottomNav';
import HeartIcon from '../../assets/icons/material_24/favorite.svg';
import TextBtn from '../atoms/TextBtn';
import useItemDetail from '../../hooks/useItemDetail';

function ItemDetailPage() {
  const { itemId } = useParams();
  const data = useItemDetail(itemId);
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
            <SContent>{data.memberId}</SContent>
            <SCategory>직거래만 합니다</SCategory>
          </div>
        </SProfile>
        <STitle>{data.title}</STitle>
        <SCategory>
          {data.itemCategories} | {data.regDate}
        </SCategory>
        <SContent>{data.content}</SContent>
        <DetailBottomNav>
          <img src={HeartIcon} alt="" />
          <div>{data.price}원</div>
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
export default ItemDetailPage;
