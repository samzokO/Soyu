import { useState, useEffect } from 'react';
import { getHistoryList } from '../api/apis';

function useHistoryList() {
  const [data, setData] = useState('');
  useEffect(() => {
    getHistoryList().then((response) => {
      console.log(response);
      const result = JSON.stringify(response.data.data);
      setData(result);
    });
  }, []);
  return data;
}
export default useHistoryList;
