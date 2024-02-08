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
<<<<<<< HEAD
      .then((response) => {
        // spring에서 발급된 jwt 반환 localStorage 저장
        localStorage.setItem('accessToken', response.data.data.accessToken);
        localStorage.setItem('refreshToken', response.data.data.refreshToken);

        // 메인 페이지로 이동
        window.location.href = '/';
      })
      .catch(() => {
        // 에러발생 시 login 페이지로 전환
        window.location.href = '/login';
=======
      .then(({ data }) => {
        const token = data.data.accessToken;
        localStorage.setItem('accessToken', token);
        localStorage.setItem('memberId', jwtDecode(token).sub);
        navigate('/');
      })
      .catch(() => {
        navigate('/login');
>>>>>>> b7e70a5 (refactor: useNaverCallback 로직 변경 및 토큰 파싱 구현)
      });
  }, []);
}

export default useNaverCallback;
