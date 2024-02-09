import { useEffect, useState } from 'react';
import { getFavorite } from '../api/apis';

function useBookmark() {
  const [data, setData] = useState();
  useEffect(() => {
    getFavorite(data)
      .then((response) => {
        const res = response.data.data;
        setData(res);
      })
      .catch(() => {
        setData([]);
      });
  }, []);
  return data;
}

export default useBookmark;
