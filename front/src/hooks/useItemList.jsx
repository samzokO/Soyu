import { useState, useEffect } from 'react';
import { getItemList } from '../api/apis';

function useItemList() {
  const [data, setData] = useState('');
  useEffect(() => {
    getItemList().then((response) => {
      setData(JSON.stringify(response.data.data));
    });
  }, []);
  return data;
}
export default useItemList;
