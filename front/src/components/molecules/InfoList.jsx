import { useEffect, useState } from 'react';
import styled from 'styled-components';
import { motion } from 'framer-motion';
import { Link } from 'react-router-dom';
import font from '../../styles/font';
import AccountWallet from '../../assets/icons/material_24/account_wallet.svg';
import Edit from '../../assets/icons/material_24/edit.svg';
import ImageSearch from '../../assets/icons/material_24/image_search.svg';
import Modal from './modal/Modal';
import {
  transitionList,
  transitionListitem,
} from '../../styles/ListTransition';

function InfoList() {
  const [isOpen, setIsOpen] = useState(false);
  const onClickButton = () => {
    console.log(isOpen);
    setIsOpen(true);
    console.log(isOpen);
  };
  useEffect(() => {
    console.log('ㅅㅂ');
  }, [isOpen]);
  return (
    <>
      <SH2>내 정보</SH2>
      <motion.ul variants={transitionList} initial="hidden" animate="visible">
        <SLi variants={transitionListitem}>
          <SFlexLink to="account">
            <SImg src={AccountWallet} alt="#" />
            <p>계좌번호 변경</p>
          </SFlexLink>
        </SLi>
        <SLi variants={transitionListitem}>
          <SFlexLink onClick={onClickButton}>
            <SImg src={ImageSearch} alt="#" />
            <p>프로필 이미지 변경</p>
            {isOpen && (
              <Modal
                open={isOpen}
                onClose={() => {
                  console.log(isOpen);
                  setIsOpen(false);
                  console.log(isOpen);
                }}
              />
            )}
          </SFlexLink>
        </SLi>
        <SLi variants={transitionListitem}>
          <SFlexLink to="/auth">
            <SImg src={Edit} alt="#" />
            <p>닉네임 변경</p>
          </SFlexLink>
        </SLi>
      </motion.ul>
    </>
  );
}

export default InfoList;

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
