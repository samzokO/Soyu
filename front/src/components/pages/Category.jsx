import styled from 'styled-components';
import TextTag from '../atoms/TextTag';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import BottomNav from '../molecules/BottomNav';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import useCategory from '../../hooks/useCategory';
import ItemList from '../molecules/ItemList';
import theme from '../../styles/theme';

function Category() {
  const [data, categoryChanger] = useCategory();
  const itemCategory = [
    { name: '가전', value: 'ELECTRONICS' },
    { name: '의류', value: 'CLOTHING' },
    { name: '가구', value: 'FURNITURE' },
    { name: '책', value: 'BOOKS' },
    { name: '스포츠', value: 'SPORTS' },
  ];
  return (
    <>
      <LocalHeader>
        <BackBtn />
        카테고리 검색
        <div />
      </LocalHeader>
      <MainContainerWithoutNav>
        <SList>
          {itemCategory.map((item, index) => (
            <STag
              key={index}
              value={item.value}
              onClick={() => categoryChanger(item.value)}
            >
              {item.name}
            </STag>
          ))}
        </SList>
        {data && <ItemList title="검색결과" data={data} />}
      </MainContainerWithoutNav>
      <BottomNav />
    </>
  );
}
const STag = styled.button`
  ${theme.font.Badge};
  padding: 7px;
  min-width: 40px;
  border: 1.5px black solid;
  border-radius: 15px;
`;

const SList = styled.div`
  padding: 10px 0px;
  display: flex;
  gap: 10px;
`;

export default Category;
