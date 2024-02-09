import { useEffect, useState } from 'react';
import { getStation } from '../api/apis';

function useStationList() {
  const [data, setData] = useState();
  useEffect(() => {
    getStation().then((response) => {
      const result = response.data.data;
      setData(result);
    });
  }, []);
  return data;
}

export default useStationList;
