import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import { getNaverCode } from '../api/apis';

function useNaverCallback() {
  const navigate = useNavigate();
  const searchParams = useSearchParams()[0];

  useEffect(() => {
    const code = searchParams.get('code');
    const state = searchParams.get('state');
    getNaverCode(code, state)
      .then(({ data }) => {
        const token = data.data.accessToken;
        const refresh = data.data.refreshToken;
        localStorage.setItem('accessToken', token);
        localStorage.setItem('refreshToken', refresh);
        localStorage.setItem('memberId', jwtDecode(token).sub);
        navigate('/');
      })
      .catch(() => {
        navigate('/login');
      });
  }, []);
}

export default useNaverCallback;
