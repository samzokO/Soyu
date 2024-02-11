import styled from 'styled-components';
import { Link } from 'react-router-dom';
import font from '../../styles/font';
import Badge from '../atoms/Badge';
import theme from '../../styles/theme';
import Heart from '../../assets/icons/material_24/favorite.svg';

function Station({ data }) {
  return (
    <article>
      <STitle>{data.stationName}</STitle>
      <SUl>
        {data?.lockers.map(
          (item) =>
            item.title && (
              <SLi key={item.itemId} to={`/item/${item.itemId}`}>
                <SNumber>{item.lockerLocation}</SNumber>
                <SImg src="#" alt="#" />
                <SPadding>
                  <SFlexWrap>
                    <SSubTitle>{item.title}</SSubTitle>
                    <SFlexHeart>
                      <SIcon src={Heart} alt="좋아요 수" />
                      <SBody2>{item.likeCount}</SBody2>
                    </SFlexHeart>
                  </SFlexWrap>
                  <SPrice>
                    {'itemStatus' !== 5 && <Badge status={0} />}
                    <div>{item.price}원</div>
                  </SPrice>
                </SPadding>
              </SLi>
            ),
        )}
      </SUl>
    </article>
  );
}

export default Station;

const SPadding = styled.div`
  padding: 0px 2px;
`;

const SPrice = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const SIcon = styled.img`
  width: 12px;
  height: 12px;
`;

const SFlexHeart = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1px;
  padding: 0px 3px;
  white-space: nowrap;
`;

const SFlexWrap = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  min-width: 20px;
  width: 100%;
`;

const SNumber = styled.div`
  position: absolute;
  top: 0px;
  left: 0px;
  width: 25px;
  border-radius: 7px 2px 7px 2px;
  height: 25px;
  ${font.Body1};
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${theme.color.secondaryColor};
`;

const SLi = styled(Link)`
  width: 150px;
  flex-grow: 0;
  height: 160px;
  display: flex;
  flex-direction: column;
  margin: 5px;
  padding: 5px;
  border-radius: 7px;
  position: relative;
  gap: 5px;
  transition: all 0.3s cubic-bezier(0, 0, 0.5, 1);
  box-shadow: 2px 4px 12px rgba(0, 0, 0, 0.08);
  &:hover {
    box-shadow: 2px 4px 16px rgba(0, 0, 0, 0.16);
    cursor: pointer;
  }
`;

const SBody2 = styled.span`
  ${font.Body2}
`;

const SSubTitle = styled.span`
  ${font.Subtitle}
  white-space: nowrap;
  text-overflow: ellipsis;
  overflow: hidden;
  max-width: 100px;
`;

const STitle = styled.h2`
  ${font.Title}
  margin: 30px 0 16px;
`;

const SImg = styled.img`
  height: 100px;
  border: 1px solid black;
  border-radius: 5px;
`;

const SUl = styled.ul`
  display: flex;
  overflow: scroll;
`;
