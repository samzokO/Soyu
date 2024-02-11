import { Navigate } from 'react-router-dom';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { deleteMember } from '../api/apis';

function useSignout() {
  const signout = () => {
    deleteMember()
      .then((res) => {
        if (res.response.status === 200) {
          localStorage.removeItem('accessToken');
          localStorage.removeItem('refreshToken');
          localStorage.removeItem('fcmToken');
          Navigate('/login');
        } else {
          toast.error(`${res.response.data.message}`, {
            position: 'top-center',
          });
        }
      })
      .catch((error) => {
        toast.error(`${error.response.data.message}`, {
          position: 'top-center',
        });
      });
  };
  return signout;
}

export default useSignout;
