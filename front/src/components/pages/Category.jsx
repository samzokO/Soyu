import styled from 'styled-components';
import TextTag from '../atoms/TextTag';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import BottomNav from '../molecules/BottomNav';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';

function Category() {
  return (
    <>
      <LocalHeader>
        <BackBtn />
        카테고리 검색
        <div />
      </LocalHeader>
      <MainContainerWithoutNav>
        <SList>
          <TextTag>가전</TextTag>
          <TextTag>주류</TextTag>
          <TextTag>주주</TextTag>
          <TextTag>바바</TextTag>
        </SList>
      </MainContainerWithoutNav>
      <BottomNav />
    </>
  );
}

const SList = styled.div`
  padding: 10px 0px;
  display: flex;
  gap: 10px;
`;

export default Category;
