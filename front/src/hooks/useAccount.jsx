import { useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { patchAccount, deleteAccount, getAccount } from '../api/apis';

function useAccount() {
  const [data, setData] = useState();
  const getHandler = () =>
    new Promise((resolve, reject) => {
      const ress = getAccount()
        .then((res) => {
          const result = res?.data?.data;
          setData(result);
          return result;
        })
        .catch(() => {
          toast.error(`등록된 계좌가 없습니다.`, {
            position: 'top-center',
          });
        });
      if (ress) {
        resolve(ress);
      } else {
        reject(new Error('Something went wrong'));
      }
    });

  const patchHandler = (bankName, accountNumber) => {
    console.log(bankName);
    console.log(accountNumber);
    patchAccount(bankName, accountNumber).then(() => {
      console.log('된거임?');
    });
  };
  const deleteHandler = () => {
    deleteAccount().then((res) => {
      if (!data) {
        toast.error(`등록된 계좌가 없습니다.`, {
          position: 'top-center',
        });
      } else if (res.data?.statusCode === 200) {
        setData();
        toast.success(`계좌 정보가 삭제되었습니다.`, {
          position: 'top-center',
        });
      } else {
        toast.error(`문제가 발생했습니다.`, {
          position: 'top-center',
        });
      }
    });
  };

  return [data, getHandler, patchHandler, deleteHandler];
}

export default useAccount;
