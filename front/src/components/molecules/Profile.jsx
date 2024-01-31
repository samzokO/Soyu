import styled from 'styled-components';
import font from '../../styles/font';
import color from '../../styles/color';
import defaultImage from '../../assets/icons/material_24/default_account.svg';

function Profile() {
  return (
    <SFlexWrap>
      <SImg src={defaultImage} alt="프로필 이미지" />
      <div>
        <SH2>유저명</SH2>
        <SP>농협 010-0100-0100</SP>
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
