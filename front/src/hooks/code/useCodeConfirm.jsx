import { useState } from 'react';
import { getSellerCode, getPurchaseCode } from '../../api/apis';
import { showSuccessToast } from '../../utils/toastUtil';

function useCodeConfirm() {
  const [data, setData] = useState();
  const sellerPCode = (itemId) => {
    try {
      getSellerCode(itemId)
        .then((res) => {
          showSuccessToast(`확인코드 : ${res.data.data}`);
        })
        .catch((e) => {
          console.log(e);
        });
    } catch (error) {
      console.log(error);
    }
  };

  const buyerPCode = (itemId) => {
    try {
      getPurchaseCode(itemId)
        .then((res) => {
          showSuccessToast(`확인코드 : ${res.data.data}`);
        })
        .catch((e) => {
          console.log(e);
        });
    } catch (error) {
      console.log(error);
    }
  };

  return [data, sellerPCode, buyerPCode];
}
export default useCodeConfirm;
