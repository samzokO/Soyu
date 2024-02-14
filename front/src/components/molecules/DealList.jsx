import styled from 'styled-components';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import font from '../../styles/font';
import Favorite from '../../assets/icons/material_24/favorite.svg';
import Receipt from '../../assets/icons/material_24/receipt.svg';
import Inventory from '../../assets/icons/material_24/inventory.svg';
import ShoppingBag from '../../assets/icons/material_24/shopping_bag.svg';

function DealList() {
  const list = {
    hidden: {
      opacity: 0,
    },
    visible: {
      opacity: 1,
      transition: {
        when: 'beforeChildren',
        staggerChildren: 0.1,
      },
    },
  };

  const listitem = {
    hidden: { opacity: 0, y: 50 },
    visible: { opacity: 1, y: 0 },
  };
  return (
    <>
      <SH2>내 거래</SH2>
      <motion.ul variants={list} initial="hidden" animate="visible">
        <SLi variants={listitem}>
          <SFlexLink to="buylist">
            <SImg src={ShoppingBag} alt="#" />
            <p>구매 내역</p>
          </SFlexLink>
        </SLi>
        <SLi variants={listitem}>
          <SFlexLink to="selllist">
            <SImg src={Receipt} alt="#" />
            <p>판매 내역</p>
          </SFlexLink>
        </SLi>
        <SLi variants={listitem}>
          <SFlexLink to="/bookmark/heart">
            <SImg src={Favorite} alt="#" />
            <p>찜 목록</p>
          </SFlexLink>
        </SLi>
        <SLi variants={listitem}>
          <SFlexLink to="/bookmark/">
            <SImg src={Inventory} alt="#" />
            <p>즐겨찾기 목록</p>
          </SFlexLink>
        </SLi>
      </motion.ul>
    </>
  );
}

export default DealList;

const SH2 = styled.h2`
  ${font.Title}
  margin: 20px 0 16px;
`;

const SLi = styled(motion.li)`
  margin-bottom: 11px;
`;

const SFlexLink = styled(Link)`
  display: flex;
  align-items: center;
`;

const SImg = styled.img`
  margin-right: 5px;
`;
