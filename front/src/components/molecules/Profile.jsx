import styled from 'styled-components';
import font from '../../styles/font';
import color from '../../styles/color';
import defaultImage from '../../assets/icons/material_24/default_account.svg';

function Profile({ img, nickName, bankName, accountNumber }) {
  return (
    <SFlexWrap>
      <SImg
        src={img ?? 'https://source.unsplash.com/random/250x250/?character'}
        alt="프로필 이미지"
      />
      <div>
        {nickName && <SH2>{nickName}</SH2>}
        <SP>
          내 계좌 - {bankName} {accountNumber}
        </SP>
      </div>
    </SFlexWrap>
  );
}

export default Profile;

const SP = styled.p`
  height: inherit;
  ${font.Body1};
`;

const SFlexWrap = styled.div`
  display: flex;
  border-radius: 5px;
  padding: 20px;
  background-color: ${color.secondaryColor};
`;

const SImg = styled.img`
  margin-right: 10px;
  width: 80px;
  height: 80px;
  background-color: white;
  border-radius: 40px;
`;

const SH2 = styled.h2`
  ${font.Title}
  margin: 10px 0 15px;
`;
