import { useState, useEffect } from 'react';
import { getNotice } from '../api/apis';

function useNotification() {
  const [notices, setNotices] = useState([]);
  useEffect(() => {
    (async () => {
      const { data } = await getNotice();
      setNotices(data.data);
    })();
  }, []);
  return notices;
}

export default useNotification;
