import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';
import { patchNickname } from '../api/apis';
import { validateName } from '../utils/validationUtil';

function useChangeNickname() {
  const navigate = useNavigate();

  const changeNick = (nickname) => {
    if (!validateName(nickname)) {
      toast.error(`한글 또는 영어를 포함해 공백없이 2~10글자로 작성해주세요.`, {
        position: 'top-center',
      });
    } else {
      patchNickname(nickname)
        .then((res) => {
          if (res.response?.status === 200 || res.status === 200) {
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
    }
  };
  return changeNick;
}

export default useChangeNickname;
