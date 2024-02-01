import styled from 'styled-components';
import BackBtn from '../atoms/BackBtn';
import TextBtn from '../atoms/TextBtn';
import LocalHeader from '../molecules/LocalHeader';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import TextField from '../molecules/TextField';
import TextArea from '../molecules/TextArea';
import PictureAddBtn from '../atoms/PictureAddBtn';

function ProductAddPage() {
  return (
    <>
      <LocalHeader>
        <BackBtn />
        물품 등록하기
        <TextBtn content="등록" />
      </LocalHeader>
      <Con>
        <TextField title="제목" placeholder="제목을 입력해주세요." />
        <TextField title="가격" placeholder="가격을 입력해주세요." />
        <TextArea title="내용" placeholder="자세한 설명을 작성해주세요." />
        <TextField title="카테고리" placeholder="카테고리를 선택해주세요" />
        <PictureAddBtn />
      </Con>
    </>
  );
}

const Con = styled(MainContainerWithoutNav)`
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 64px;
`;

export default ProductAddPage;
