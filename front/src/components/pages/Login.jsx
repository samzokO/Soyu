import styled from 'styled-components';
import Logo from '../../assets/Logo.svg';
import KakaoLogin from '../atoms/KakaoLogin';
import NaverLogin from '../atoms/NaverLogin';
import GoogleLogin from '../atoms/GoogleLogin';

function Login() {
  return (
    <SWrap>
      <img src={Logo} alt="#" />
      <STitle>소유박스</STitle>
      <SNav>
        <NaverLogin />
        <KakaoLogin />
        <GoogleLogin />
      </SNav>
    </SWrap>
  );
}

export default Login;

const STitle = styled.h1`
  font-size: 24px;
  margin: 20px 0;
`;

const SWrap = styled.div`
  width: 100vw;
  height: 100vh;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;

const SNav = styled.nav`
  width: 200px;
  & * {
    margin-bottom: 10px;
  }
`;
