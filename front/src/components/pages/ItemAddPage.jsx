import styled from 'styled-components';
import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';
import BackBtn from '../atoms/BackBtn';
import TextBtn from '../atoms/TextBtn';
import LocalHeader from '../molecules/LocalHeader';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import TextField from '../molecules/TextField';
import TextArea from '../molecules/TextArea';
import PictureAddBtn from '../atoms/PictureAddBtn';
import { postImg } from '../../api/apis';
import SelectBox from '../atoms/SelectBox';
import useAccount from '../../hooks/useAccount';

function ItemAddPage() {
  const [account, getAccount] = useAccount();
  const [files, setFiles] = useState();
  const [Data, setData] = useState({
    title: '',
    content: '',
    price: '',
    itemCategories: '',
  });
  const navigate = useNavigate();

  useEffect(() => {
    getAccount();
    if (!account) {
      toast.error(`계좌등록을 먼저 해주세요`, {
        position: 'top-center',
      });
      navigate('/');
    }
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (Object.values(Data).some((value) => !value) || !files) {
      toast.error(`마저 작성하세요`, {
        position: 'top-center',
      });
      return;
    }
    postImg(Data, files).then((res) => {
      if (res.status === 200) {
        toast.success(`작성완료`, {
          position: 'top-center',
        });
        navigate('/');
      }
    });
  };

  const handleUpload = (e) => {
    e.preventDefault();
    const file = e.target.files;
    console.log(file);
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
    <form action="" encType="multipart/form-data" onSubmit={handleSubmit}>
      <LocalHeader>
        <BackBtn />
        물품 등록하기
        <TextBtn type="submit">등록</TextBtn>
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
          name="itemCategories"
          value={Data.itemCategories}
          onChange={handleChange}
          options={options}
        />

        <PictureAddBtn
          id="file"
          name="file"
          type="file"
          onChange={handleUpload}
        />
      </Con>
    </form>
  );
}

const Con = styled(MainContainerWithoutNav)`
  display: flex;
  flex-direction: column;
  gap: 20px;
  margin-top: 64px;
`;

export default ItemAddPage;
