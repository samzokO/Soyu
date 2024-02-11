import { useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { patchAccount, deleteAccount, getAccount } from '../api/apis';

function useAccount() {
  const [data, setDate] = useState();
  const getHandler = () => {
    getAccount()
      .then((res) => {
        const result = res?.data?.data;
        setDate(result);
      })
      .catch((e) => {});
  };
  const patchHandler = (bankName, accountNumber) => {
    patchAccount(bankName, accountNumber).then((res) => {});
  };
  const deleteHandler = () => {
    deleteAccount().then((res) => {
      if (!data) {
        toast.error(`등록된 계좌가 없습니다.`, {
          position: 'top-center',
        });
      } else if (res.data?.statusCode === 200) {
        setDate();
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
