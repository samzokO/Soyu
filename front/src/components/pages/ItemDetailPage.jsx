import { useEffect } from 'react';
import styled from 'styled-components';
import { useNavigate, useParams } from 'react-router-dom';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import ImageSlide from '../molecules/ImageSlide';
import LocalHeader from '../molecules/LocalHeader';
import theme from '../../styles/theme';
import ImageCircle from '../atoms/ImageCircle';
import DetailBottomNav from '../molecules/DetailBottomNav';
import HeartIcon from '../../assets/icons/material_24/favorite.svg';
import Heart from '../../assets/icons/material_24/heart.png';
import useItemDetail from '../../hooks/useItemDetail';
import useLikeToggle from '../../hooks/useLikeToggle';
import useLoadImg from '../../hooks/useLoadImg';
import { useTimeStamp } from '../../hooks/useTimeStamp';
import useMakeTransaction from '../../hooks/useMakeTransaction';
import { deleteItemStatus } from '../../api/apis';

function ItemDetailPage() {
  const { itemId } = useParams();
  const [img, loadImage] = useLoadImg();
  const data = useItemDetail(itemId);
  const [like, setLike, changer] = useLikeToggle();
  useEffect(() => {
    if (data.likesStatus !== undefined) {
      setLike(data.likesStatus);
      loadImage(data.imageResponses);
    }
  }, [data.likesStatus]);
  const date = useTimeStamp(data.regDate);
  const onClickHandler = useMakeTransaction();
  const navigate = useNavigate();

  return (
    <>
      <LocalHeader>
        <BackBtn />
        <div />
        <button
          type="button"
          onClick={() => deleteItemStatus(itemId).then(() => navigate(-1))}
        >
          삭제
        </button>
      </LocalHeader>
      <ImageSlide data={img} />
      <SMainContainer>
        <SProfile>
          <ImageCircle />
          <div>
            <SCategory>판매자</SCategory>
            <SContent>{data.nickname}</SContent>
          </div>
        </SProfile>
        <STitle>{data.title}</STitle>
        <SCategory>
          {data.itemCategories} | {date}
        </SCategory>
        <SContent>{data.content}</SContent>
        <DetailBottomNav>
          <button type="button" onClick={() => changer(data.itemId)}>
            {like ? <img src={Heart} alt="" /> : <img src={HeartIcon} alt="" />}
          </button>
          <div>{data.price}원</div>
          <button
            type="button"
            onClick={() =>
              onClickHandler(
                data.itemId,
                localStorage.getItem('memberId'),
                data.memberId,
              )
            }
          >
            채팅하기
          </button>
        </DetailBottomNav>
      </SMainContainer>
    </>
  );
}

const SMainContainer = styled(MainContainerWithNav)`
  height: 100%;
  margin: 10px 0px 53px 0px;
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const SProfile = styled.div`
  padding: 10px;
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
