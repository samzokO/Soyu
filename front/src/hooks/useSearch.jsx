import { useState, useEffect } from 'react';
import { getKeyword } from '../api/apis';

function useSearch(keyword) {
  const [data, setData] = useState('');
  // eslint-disable-next-line
  const [key, setKey] = useState('');
  const keywordHandler = (k) => {
    setKey(k);
  };
  useEffect(() => {
    getKeyword(keyword).then((response) => {
      const result = JSON.stringify(response.data.data);
      setData(result);
    });
  }, []);
  return [data, keywordHandler];
}
export default useSearch;
