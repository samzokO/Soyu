import { useState, useEffect } from 'react';
import { getItemList } from '../api/apis';

function useItemList() {
  const [data, setData] = useState('');
  useEffect(() => {
    getItemList().then((response) => {
      const result = JSON.stringify(response.data.data);
      setData(result);
    });
  }, []);
  return data;
}
export default useItemList;
