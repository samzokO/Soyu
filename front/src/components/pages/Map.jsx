import styled from 'styled-components';
import { MainContainerWithNav } from '../../styles/Maincontainer';
import BackBtn from '../atoms/BackBtn';
import LocalHeader from '../molecules/LocalHeader';
import theme from '../../styles/theme';
import Heart from '../../assets/icons/material_24/favorite.svg';
import BottomNav from '../molecules/BottomNav';

function Map() {
  return (
    <div>
      <LocalHeader>
        <BackBtn />
        소유박스 지도
        <div />
      </LocalHeader>
      <MainContainerWithNav>
        <SHeadLine>소유박스는 어디있을까요?</SHeadLine>
        <SList>
          <Box>
            <SImg src="/icons/favicon-96x96.png" alt="소유박스 사진" />
            <SContent>
              <div>
                <STitle>소유박스 삼성화재 유성캠퍼스점</STitle>
                <SBody2>위치 : 대전 유성구 동서대로 98-39</SBody2>
              </div>
              <div>현재 이용가능 박스 수 : 1 / 3</div>
              <SHeart>
                <img src={Heart} alt="" />
              </SHeart>
            </SContent>
          </Box>
          <Box>
            <SImg src="/icons/favicon-96x96.png" alt="소유박스 사진" />
            <SContent>
              <div>
                <STitle>소유박스 삼성화재 유성캠퍼스점</STitle>
                <SBody2>위치 : 대전 유성구 동서대로 98-39</SBody2>
              </div>
              <div>현재 이용가능 박스 수 : 1 / 3</div>
              <SHeart>
                <img src={Heart} alt="" />
              </SHeart>
            </SContent>
          </Box>
        </SList>
      </MainContainerWithNav>
      <BottomNav />
    </div>
  );
}

const SImg = styled.img`
  border-radius: 7px;
`;

const SList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 10px;
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

const SHeadLine = styled.div`
  ${theme.font.Subtitle}
  padding: 10px 0px;
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

export default Map;
