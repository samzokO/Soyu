import styled from 'styled-components';
import ItemBox from '../atoms/ItemBox';
import theme from '../../styles/theme';

/** 제목과 아이템 데이터(배열)를 넣으면 리스트로 표시
 * @params (String)title - 리스트 제목
 * @params (Json)data -파싱된 Item JSON 데이터
 */
function ItemList({ title, data }) {
  return (
    <SProductList>
      <STitle>{title}</STitle>
      {data &&
        JSON.parse(data).map((item) => (
          <ItemBox
            key={item.itemId}
            itemId={item.itemId}
            title={item.title}
            regDate={item.regDate}
            itemStatus={0}
            price={item.price}
          />
        ))}
    </SProductList>
  );
}

const SProductList = styled.article`
  padding-top: 10px;
  padding-bottom: 53px;
`;

const STitle = styled.div`
  ${theme.font.Title}
`;

export default ItemList;
