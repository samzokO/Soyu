import { useState } from 'react';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';
import { patchNickname } from '../api/apis';

function useChangeNickname() {
  const [data, setData] = useState();
  const navigate = useNavigate();

  const changeNick = (nickname) => {
    patchNickname(nickname)
      .then((res) => {
        if (res.response?.status === 200 || res.status === 200) {
          setData(nickname);
          navigate(-1);
        } else {
          throw new Error(
            res.response.data.message ?? '알 수 없는 오류가 발생했어요.',
          );
        }
      })
      .catch((error) => {
        toast.error(`${error}`, {
          position: 'top-center',
        });
      });
  };
  return [data, changeNick];
}

export default useChangeNickname;
