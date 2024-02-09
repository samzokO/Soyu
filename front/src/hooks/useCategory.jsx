import { useState, useEffect } from 'react';
import { getCategory } from '../api/apis';

function useCategory() {
  const [data, setData] = useState('');
  const [result, setResult] = useState('');
  const categoryChanger = (value) => {
    setData(value);
  };
  useEffect(() => {
    if (data)
      getCategory(data).then((response) => {
        const res = response.data.data;
        setResult(res);
      });
  }, [data]);
  return [result, categoryChanger];
}
export default useCategory;
