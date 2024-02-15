import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { kioskSell, kioskWithdraw, kioskBuy } from '../api/apis';
import { showErrorToast, showSuccessToast } from '../utils/toastUtil';

function useKiosk() {
  const [data, setData] = useState();
  const navigate = useNavigate();

  const selling = (code) => {
    try {
      kioskSell(code).then((res) => {
        if (res?.data?.data?.lockerNum) {
          navigate(`/forsale/${res?.data?.data?.lockerNum}`);
        }
      });
    } catch {
      console.log();
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
      kioskWithdraw(code).then((res) => {
        if (res?.data?.data?.lockerNum) {
          navigate(`/forsale/${res?.data?.data?.lockerNum}`);
        }
      });
    } catch {
      console.log();
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
        if (res.status === 200) {
          const id = res?.data?.data?.itemId;
          const num = res?.data?.data?.lockerNum;
          setData(res?.data?.data);
          showSuccessToast(`성공?  ${num} ${id}`);
          navigate(`purchase/${num}/${id}`);
        } else {
          showErrorToast(`${res?.response?.data?.message}`);
        }
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
