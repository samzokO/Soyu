import { useState, useEffect } from 'react';
import { kioskSell, kioskWithdraw } from '../api/apis';

function useKiosk(type, code) {
  const [data, setData] = useState('');
  useEffect(() => {
    if (type === 'sell') {
      console.log('판매다');
      kioskSell(code).then((response) => {
        const result = response.data.data;
        setData(result);
      });
    } else if (type === 'withdraw') {
      console.log('회수다');
      kioskWithdraw(code).then((response) => {
        const result = response.data.data;
        setData(result);
      });
    }
  }, [type, code]);
  return data;
}
export default useKiosk;
