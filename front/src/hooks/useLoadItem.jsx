import { useEffect, useState } from 'react';
import { getItem } from '../api/apis';

function useLoadItem(itemId) {
  const [goodsImageURL, setGoodsImageURL] = useState('');
  const [goodsName, setGoodsName] = useState('');
  const [goodsPrice, setGoodsPrice] = useState(0);
  const [goodsStatus, setGoodsStatus] = useState('');

  useEffect(() => {
    (async () => {
      const { data } = await getItem(itemId);
      setGoodsImageURL(data.data.imageResponses);
      setGoodsName(data.data.title);
      setGoodsPrice(data.data.price);
      setGoodsStatus(data.data.itemStatus);
    })();
  }, []);

  return [goodsImageURL, goodsName, goodsPrice, goodsStatus];
}

export default useLoadItem;
