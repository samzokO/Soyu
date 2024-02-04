import { useEffect } from 'react';
import styled from 'styled-components';
import { getNaverCode } from '../api/apis';

function useNaverCallback() {
  useEffect(() => {
    const code = new URL(window.location.href).searchParams.get('code');
    const state = new URL(window.location.href).searchParams.get('state');
    getNaverCode(code, state)
      .then((response) => {
        // spring에서 발급된 jwt 반환 localStorage 저장
        localStorage.setItem('accessToken', response.data.data.accessToken);
        // 메인 페이지로 이동
        window.location.href = '/';
      })
      .catch(() => {
        // 에러발생 시 login 페이지로 전환
        // alert(err.response.data.detail);
        window.location.href = '/login';
      });
  }, []);

  return (
    <SErrorDiv>
      <h1>이동중입니다</h1>
      <p>loading...</p>
    </SErrorDiv>
  );
}

const SErrorDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100vh;
  gap: 1em;
`;

export default useNaverCallback;
