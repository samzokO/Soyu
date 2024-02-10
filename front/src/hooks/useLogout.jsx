import { useNavigate } from 'react-router-dom';
import { postLogout } from '../api/apis';

function useLogout() {
  const navigate = useNavigate();
  const handleLogout = () => {
    postLogout().then(() => {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      navigate('/login');
    });
  };

  return handleLogout;
}

export default useLogout;
