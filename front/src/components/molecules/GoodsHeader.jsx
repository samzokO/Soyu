import { useNavigate, useParams } from 'react-router-dom';
import { useEffect } from 'react';
import styled from 'styled-components';
import font from '../../styles/font';
import Button from '../atoms/Button';
import color from '../../styles/color';
import useLoadItem from '../../hooks/useLoadItem';
import useLoadImg from '../../hooks/useLoadImg';

function GoodsHeader({ itemId }) {
  const [imageURL, name, price] = useLoadItem(itemId);
  const navigate = useNavigate();
  const { chatId } = useParams();
  const [img, loadImg] = useLoadImg();
  useEffect(() => {
    loadImg(imageURL);
  }, []);
  return (
    <SFlex>
      <SImg src={img} alt="물건 사진" />
      <SDiv>
        <SH2>{name}</SH2>
        <SPrice>{price}원</SPrice>
      </SDiv>
      <Button
        type="1"
        onClick={() => {
          navigate(`/station/${chatId}`);
        }}
      >
        거래약속
      </Button>
    </SFlex>
  );
}
export default GoodsHeader;

const SDiv = styled.div`
  width: 100%;
`;

const SFlex = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 0 16px;
  border-bottom: 1px solid ${color.grayScale200};
`;

const SImg = styled.img`
  min-width: 50px;
  height: 50px;
  border-radius: 7px;
`;

const SH2 = styled.h2`
  ${font.Title}
  overflow: hidden;
`;

const SPrice = styled.p`
  ${font.Body1}
  width: auto;
  overflow: hidden;
`;
