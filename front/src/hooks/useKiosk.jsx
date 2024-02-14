import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { kioskSell, kioskWithdraw, kioskBuy } from '../api/apis';
import { showSuccessToast } from '../utils/toastUtil';

function useKiosk() {
  const [data, setData] = useState();
  const navigate = useNavigate();

  const selling = (code) => {
    try {
      kioskSell(code).then((res) => {
        console.log(res);
        console.log(res.data.data.lockerNum);
      });
    } catch (error) {
      console.error('Error while selling:', error);
    }
    return new Promise((resolve, reject) => {
      if (data) {
        resolve(data);
      } else {
        reject(new Error('바르지 않은 코드입니다.'));
      }
    });
  };

  const withdrawal = (code) => {
    try {
      const response = kioskWithdraw(code);
      const result = response;
      setData(result);
      kioskWithdraw(code).then((res) => {
        console.log(res);
      });
    } catch (error) {
      console.error('Error while withdrawing:', error);
    }
    return new Promise((resolve, reject) => {
      if (data) {
        resolve(data);
      } else {
        reject(new Error('바르지 않은 코드입니다.'));
      }
    });
  };

  const purchase = (code) => {
    try {
      kioskBuy(1, code).then((res) => {
        console.log(res);
        const id = res?.data?.data?.itemId;
        const num = res?.data?.data?.lockerNum;
        setData(res.data.data);
        showSuccessToast(`성공?  ${num} ${id}`);
        navigate(`purchase/${num}/${id}`);
      });
    } catch (error) {
      console.error('Error while purchasing:', error);
    }
    return new Promise((resolve, reject) => {
      if (data) {
        resolve(data);
      } else {
        reject(new Error('바르지 않은 코드입니다.'));
      }
    }).catch((e) => {
      console.log(e);
    });
  };

  return [data, selling, withdrawal, purchase];
}

export default useKiosk;
