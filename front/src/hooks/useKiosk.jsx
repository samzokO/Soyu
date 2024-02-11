import { useState } from 'react';
import { kioskSell, kioskWithdraw, kioskBuy } from '../api/apis';

function useKiosk() {
  const [data, setData] = useState();

  const selling = (code) => {
    try {
      const response = kioskSell(code);
      setData(response);
      console.log(response);
    } catch (error) {
      console.error('Error while selling:', error);
    }
  };

  const withdrawal = (code) => {
    try {
      const response = kioskWithdraw(code);
      const result = response;
      setData(result);
      console.log(result);
      kioskWithdraw(code).then((res) => {
        console.log(res);
      });
    } catch (error) {
      console.error('Error while withdrawing:', error);
    }
  };

  const purchase = (code) => {
    try {
      const response = kioskBuy(1, code);
      const result = response;
      setData(result);
    } catch (error) {
      console.error('Error while purchasing:', error);
    }
  };

  return [data, selling, withdrawal, purchase];
}

export default useKiosk;
