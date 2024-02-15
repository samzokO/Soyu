import styled from 'styled-components';
import { motion } from 'framer-motion';
import ItemBox from '../atoms/ItemBox';
import theme from '../../styles/theme';

/** 제목과 아이템 데이터(배열)를 넣으면 리스트로 표시
 * @params (String)title - 리스트 제목
 * @params (Json)data -파싱된 Item JSON 데이터
 */
function ItemList({ title, data }) {
  const list = {
    hidden: {
      opacity: 0,
    },
    visible: {
      opacity: 1,
      transition: {
        when: 'beforeChildren',
        staggerChildren: 0.1,
      },
    },
  };
  const listitem = {
    hidden: { opacity: 0, y: 50 },
    visible: { opacity: 1, y: 0 },
  };
  return (
    <>
      <STitle>{title}</STitle>
      <SProductList variants={list} initial="hidden" animate="visible">
        {data ? (
          data?.map((item) => (
            <ItemBox
              variants={listitem}
              img={item.imageResponses[0]}
              key={item.itemId}
              itemId={item.itemId}
              title={item.title}
              itemCategories={item.itemCategories}
              regDate={item.regDate}
              itemStatus={item.itemStatus}
              price={item.price}
            />
          ))
        ) : (
          <div>죽을래dddfasdafdsfsad</div>
        )}
      </SProductList>
    </>
  );
}

const SProductList = styled(motion.article)`
  padding-top: 10px;
  padding-bottom: 53px;
  @media (min-width: 768px) {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(300px, 2fr));
    gap: 10px;
  }
`;

const STitle = styled.div`
  padding-top: 10px;
  ${theme.font.Title}
`;

export default ItemList;
