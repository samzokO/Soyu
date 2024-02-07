import { useEffect, useState } from 'react';
import { postFcm } from '../api/apis';

function useFCM(fcm) {
  const [data, setData] = useState();
  useEffect(() => {
    const res = async () => postFcm(`${fcm}`);
    setData(res);
  }, []);

  return data;
}

export default useFCM;
