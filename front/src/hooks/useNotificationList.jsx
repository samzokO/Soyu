import { useState, useEffect } from 'react';
import { getNotice } from '../api/apis';

function useNotification() {
  const [data, setData] = useState('');
  useEffect(() => {
    getNotice().then((res) => {
      const result = res.data.data;
      setData(result);
      return data;
    });
  }, []);
}

export default useNotification;
