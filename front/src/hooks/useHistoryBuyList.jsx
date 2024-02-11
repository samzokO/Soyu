import { useState, useEffect } from 'react';
import { getPurchaseHistoryList } from '../api/apis';

/** 구매내역 리스트 */
function useHistoryBuyList() {
  const [data, setData] = useState('');
  useEffect(() => {
    getPurchaseHistoryList().then((response) => {
      const result = response.data.data;
      setData(result);
    });
  }, []);
  return data;
}
export default useHistoryBuyList;
