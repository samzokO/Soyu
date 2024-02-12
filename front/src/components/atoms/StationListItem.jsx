import styled from 'styled-components';
import { useEffect } from 'react';
import Heart from '../../assets/icons/material_24/favorite.svg';
import theme from '../../styles/theme';
import FilledHeart from '../../assets/icons/material_24/heart.png';
import useFavorite from '../../hooks/useFavorite';

function StationListItem({ data }) {
  const [Handler, favorite, changer] = useFavorite(data?.stationId);
  useEffect(() => {
    changer(data?.favorite);
  }, []);
  return (
    <Box>
      <SImg src="/icons/favicon-96x96.png" alt="소유박스 사진" />
      <SContent>
        <div>
          <STitle>{data?.name}</STitle>
          <SBody2>위치 : {data?.address}</SBody2>
        </div>
        <div>현재 이용가능 박스 수 : {data?.fillCount} / 3</div>
        <SHeart type="button" onClick={() => Handler(favorite)}>
          {favorite ? (
            <img src={FilledHeart} alt="즐겨찾기 해제" />
          ) : (
            <img src={Heart} alt="즐겨찾기 등록" />
          )}
        </SHeart>
      </SContent>
    </Box>
  );
}

const SImg = styled.img`
  width: 65px;
  height: 65px;
  border-radius: 7px;
`;

const SHeart = styled.button`
  position: absolute;
  right: 10px;
  bottom: 10px;
`;

const SContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
`;

const SBody2 = styled.div`
  ${theme.font.Body2}
`;

const STitle = styled.div`
  ${theme.font.Title}
  white-space: nowrap;
`;

const Box = styled.li`
  list-style: none;
  padding: 10px 10px;
  display: flex;
  gap: 25px;
  min-width: 400px;
  max-width: 600px;
  position: relative;
  border-radius: 7px;
  transition: all 0.3s cubic-bezier(0, 0, 0.5, 1);
  box-shadow: 2px 4px 12px rgba(0, 0, 0, 0.08);
  &:hover {
    box-shadow: 2px 4px 16px rgba(0, 0, 0, 0.16);
    cursor: pointer;
  }
`;

export default StationListItem;
