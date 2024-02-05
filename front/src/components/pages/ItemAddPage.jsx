import styled from 'styled-components';
import { useState } from 'react';
import BackBtn from '../atoms/BackBtn';
import TextBtn from '../atoms/TextBtn';
import LocalHeader from '../molecules/LocalHeader';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import TextField from '../molecules/TextField';
import TextArea from '../molecules/TextArea';
import PictureAddBtn from '../atoms/PictureAddBtn';
import { postImg } from '../../api/apis';
import SelectBox from '../atoms/SelectBox';

function ItemAddPage() {
  const [files, setFiles] = useState();
  const [Data, setData] = useState({
    title: '',
    content: '',
    price: '',
    itemCategories: '',
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    postImg(Data, files);
  };

  const handleUpload = (e) => {
    e.preventDefault();
    const file = e.target.files[0];
    setFiles(file);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setData((prevFormData) => ({
      ...prevFormData,
      [name]: value,
    }));
  };

  const options = ['Books', 'Electronics', 'Clothing', 'Furniture', 'Sports'];

  return (
    <>
      <LocalHeader>
        <BackBtn />
        물품 등록하기
        <TextBtn>등록</TextBtn>
      </LocalHeader>
      <Con>
        <TextField
          title="제목"
          placeholder="제목을 입력해주세요."
          name="title"
          value={Data.title}
          onChange={handleChange}
        />
        <TextField
          title="가격"
          placeholder="가격을 입력해주세요."
          name="price"
          value={Data.price}
          onChange={handleChange}
        />
        <TextArea
          title="내용"
          placeholder="자세한 설명을 작성해주세요."
          name="content"
          value={Data.content}
          onChange={handleChange}
        />
        <SelectBox
          id="category"
          name="category"
          value="s"
          onChange={handleChange}
          options={options}
        />
        <PictureAddBtn />
        <form action="" encType="multipart/form-data" onSubmit={handleSubmit}>
          <input type="file" onChange={handleUpload} />
          <input type="submit" />
        </form>
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

export default ItemAddPage;
