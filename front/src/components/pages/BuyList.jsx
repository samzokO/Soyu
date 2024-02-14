import { styled } from 'styled-components';
import { useParams } from 'react-router-dom';
import { motion } from 'framer-motion';
import BuyGoods from '../molecules/BuyGoods';
import LocalHeader from '../molecules/LocalHeader';
import BackBtn from '../atoms/BackBtn';
import useHistoryBuyList from '../../hooks/useHistoryBuyList';
import { MainContainerWithoutNav } from '../../styles/Maincontainer';
import useHistoryList from '../../hooks/useHistoryList';

function BuyList() {
  const { list } = useParams();
  const name = list === 'buylist' ? '구매내역' : '판매내역';
  const data = list === 'buylist' ? useHistoryBuyList() : useHistoryList();
  const transitionList = {
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

  const transitionListitem = {
    hidden: { opacity: 0, y: 50 },
    visible: { opacity: 1, y: 0 },
  };
  return (
    <>
      <LocalHeader>
        <BackBtn />
        {name}
        <div />
      </LocalHeader>
      <SBuyListContainer
        variants={transitionList}
        initial="hidden"
        animate="visible"
      >
        {data &&
          data.map((item) => (
            <BuyGoods
              variants={transitionListitem}
              key={item.itemId}
              data={item}
              list={list}
              itemStatus={item.itemStatus}
            />
          ))}
        {Array.isArray(data) && data.length === 0 && (
          <div>{name} 내역 없음ㅇㅇ</div>
        )}
      </SBuyListContainer>
    </>
  );
}
const SBuyListContainer = styled(motion(MainContainerWithoutNav))`
  display: grid;
  min-height: 0px;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 10px;
`;

export default BuyList;
