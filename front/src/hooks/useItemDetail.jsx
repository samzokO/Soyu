import { useState, useEffect } from 'react';
import { getItem } from '../api/apis';

function useItemDetail(itemId) {
  const [data, setData] = useState('');
  useEffect(() => {
    getItem(itemId).then((response) => {
      const result = response.data.data;
      result.price = JSON.stringify(result.price).replace(
        /\B(?=(\d{3})+(?!\d))/g,
        ',',
      );
      setData(result);
    });
  }, []);
  return data;
}
export default useItemDetail;
