import styled from 'styled-components';
import { Link } from 'react-router-dom';
import font from '../../styles/font';
import AccountWallet from '../../assets/icons/material_24/account_wallet.svg';
import Edit from '../../assets/icons/material_24/edit.svg';
import ImageSearch from '../../assets/icons/material_24/image_search.svg';

function InfoList() {
  return (
    <>
      <SH2>내 정보</SH2>
      <ul>
        <SLi>
          <SFlexLink to="/">
            <SImg src={AccountWallet} alt="#" />
            <p>계좌번호 변경</p>
          </SFlexLink>
        </SLi>
        <SLi>
          <SFlexLink to="/">
            <SImg src={ImageSearch} alt="#" />
            <p>프로필 이미지 변경</p>
          </SFlexLink>
        </SLi>
        <li>
          <SFlexLink to="/">
            <SImg src={Edit} alt="#" />
            <p>닉네임 변경</p>
          </SFlexLink>
        </li>
      </ul>
    </>
  );
}

export default InfoList;

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
