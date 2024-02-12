import styled from 'styled-components';
import ItemBox from '../atoms/ItemBox';
import theme from '../../styles/theme';
import useLoadImg from '../../hooks/useLoadImg';

/** 제목과 아이템 데이터(배열)를 넣으면 리스트로 표시
 * @params (String)title - 리스트 제목
 * @params (Json)data -파싱된 Item JSON 데이터
 */
function ItemList({ title, data }) {
  return (
    <>
      <STitle>{title}</STitle>
      <SProductList>
        {data &&
          data?.map((item) => (
            <ItemBox
              img={item.imageResponses[0]}
              key={item.itemId}
              itemId={item.itemId}
              title={item.title}
              itemCategories={item.itemCategories}
              regDate={item.regDate}
              itemStatus={0}
              price={item.price}
            />
          ))}
      </SProductList>
    </>
  );
}

const SProductList = styled.article`
  padding-top: 10px;
  padding-bottom: 53px;
  @media (min-width: 768px) {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 10px;
  }
`;

const STitle = styled.div`
  padding-top: 10px;
  ${theme.font.Title}
`;

export default ItemList;
