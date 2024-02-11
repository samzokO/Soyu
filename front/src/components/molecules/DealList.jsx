import styled from 'styled-components';
import { Link } from 'react-router-dom';
import font from '../../styles/font';
import Favorite from '../../assets/icons/material_24/favorite.svg';
import Receipt from '../../assets/icons/material_24/receipt.svg';
import Inventory from '../../assets/icons/material_24/inventory.svg';
import ShoppingBag from '../../assets/icons/material_24/shopping_bag.svg';

function DealList() {
  return (
    <>
      <SH2>내 거래</SH2>
      <ul>
        <SLi>
          <SFlexLink to="buylist">
            <SImg src={ShoppingBag} alt="#" />
            <p>구매 내역</p>
          </SFlexLink>
        </SLi>
        <SLi>
          <SFlexLink to="selllist">
            <SImg src={Receipt} alt="#" />
            <p>판매 내역</p>
          </SFlexLink>
        </SLi>
        <SLi>
          <SFlexLink to="/bookmark/heart">
            <SImg src={Favorite} alt="#" />
            <p>찜 목록</p>
          </SFlexLink>
        </SLi>
        <SLi>
          <SFlexLink to="/bookmark/">
            <SImg src={Inventory} alt="#" />
            <p>즐겨찾기 목록</p>
          </SFlexLink>
        </SLi>
      </ul>
    </>
  );
}

export default DealList;

const SH2 = styled.h2`
  ${font.Title}
  margin: 20px 0 16px;
`;

const SLi = styled.li`
  margin-bottom: 11px;
`;

const SFlexLink = styled(Link)`
  display: flex;
  align-items: center;
`;

const SImg = styled.img`
  margin-right: 5px;
`;
