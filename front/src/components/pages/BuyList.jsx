import styled from 'styled-components';
import { useParams } from 'react-router-dom';
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
  console.log(data);
  return (
    <>
      <LocalHeader>
        <BackBtn />
        {name}
        <div />
      </LocalHeader>
      <MainContainerWithoutNav>
        {data &&
          data.map((item) => (
            <BuyGoods key={item.itemId} data={item} list={list} />
          ))}
        {Array.isArray(data) && data.length === 0 && (
          <div>마다 {name} 나이네</div>
        )}
      </MainContainerWithoutNav>
    </>
  );
}

export default BuyList;

const SWrap = styled.ul`
  padding: 16px;
`;
