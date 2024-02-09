import { useEffect, useState } from 'react';
import { getLikes } from '../api/apis';

function useLike() {
  const [data, setData] = useState();
  useEffect(() => {
    getLikes().then((res) => {
      const result = res.data.data;
      console.log(result);
      setData(result);
    });
  }, []);
  return data;
}

export default useLike;
