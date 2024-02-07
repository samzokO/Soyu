import { useState, useEffect } from 'react';
import { getMine } from '../api/apis';

function useMyPage() {
  const [data, setData] = useState('');
  useEffect(() => {
    getMine().then((response) => {
      const result = response.data.data;
      setData(result);
    });
  }, []);
  return data;
}
export default useMyPage;
